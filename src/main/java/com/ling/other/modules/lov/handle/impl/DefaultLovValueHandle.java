package com.ling.other.modules.lov.handle.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.ling.other.common.constants.BaConstants;
import com.ling.other.common.utils.PageUtils;
import com.ling.other.modules.lov.adapter.LovAdapter;
import com.ling.other.modules.lov.annotation.LovValue;
import com.ling.other.modules.lov.dto.LovDTO;
import com.ling.other.modules.lov.dto.LovValueDTO;
import com.ling.other.modules.lov.dto.TTLMap;
import com.ling.other.modules.lov.handle.LovValueHandle;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 值集处理默认处理类
 *
 * @author zhangling
 * @since 2020/7/24 16:26
 */
@Service
public class DefaultLovValueHandle implements LovValueHandle {


    private final ThreadLocal<Map<String, Map<String, String>>> valueMaps = new ThreadLocal<>();
    private final ThreadLocal<Map<String, LovDTO>> lovMaps = new ThreadLocal<>();

    @Autowired
    private LovAdapter lovAdapter;

    private static final Logger logger = LoggerFactory.getLogger(DefaultLovValueHandle.class);

    /**
     * @param targetFields data
     * @param result       接口的返回值对象CommonResult
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    @Override
    public Object process(String[] targetFields, Object result) throws NoSuchFieldException, IllegalAccessException {

        if (result == null) {
            logger.debug("lovValue is null, skip translate");
            return null;
        }
        logger.debug("lov translate begin");
        // 有其他解析需求的话可以在这里扩展
        if (result instanceof Collection) {
            // 如果传入对象为集合，则直接处理其中的Elements
            this.processCollection(targetFields, (Collection<?>) result);
        } else if (result.getClass().equals(PageUtils.class)) {
            // 处理分页数据
            this.processPage(targetFields, (PageUtils<?>) result);
        } else {
            // 未命中任何解析方式,进行默认解析
            this.processDefault(targetFields, result);
        }
        logger.debug("lov transaction end!");

        return result;
    }

    private void processPage(String[] targetFields, PageUtils<?> page) throws NoSuchFieldException, IllegalAccessException {
        Collection<?> collection = page.getList();
        if (collection == null) {
            return;
        }
        // 得到集合的第一个对象
        Optional<?> first = collection.stream().filter(Objects::nonNull).findFirst();
        if (!first.isPresent()) {
            logger.debug("target collection is empty, skip...");
            return;
        }
        Object demo = first.get();
        this.prepareValues(demo.getClass(), collection);
        for (Object object : collection) {
            if (object == null) {
                logger.debug("there is a null element in the target collection, skip null element...");
                continue;
            }
            this.processDefault(targetFields, object);
        }
    }

    /**
     * 按集合来处理传入对象
     *
     * @param targetFields 待翻译的目标字段名
     * @param collection   对象的集合
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */
    private void processCollection(String[] targetFields, Collection<?> collection) throws IllegalAccessException, NoSuchFieldException {
        if (logger.isDebugEnabled()) {
            logger.debug("process collection, target field is [{}]", Arrays.toString(targetFields));
        }
        if (CollectionUtils.isEmpty(collection)) {
            logger.debug("target collection is empty, skip...");
            return;
        }
        // 得到集合的第一个对象
        Optional<?> first = collection.stream().filter(Objects::nonNull).findFirst();
        if (!first.isPresent()) {
            logger.debug("target collection is empty, skip...");
            return;
        }
        // Object为集合第一个对象
        Object demo = first.get();
        this.prepareValues(demo.getClass(), collection);
        for (Object object : collection) {
            if (object == null) {
                logger.debug("there is a null element in the target collection, skip null element...");
                continue;
            }
            this.processDefault(targetFields, object);
        }
    }

    /**
     * 默认处理传入对象
     *
     * @param targetFields 待翻译的目标字段名
     * @param result
     */
    private void processDefault(String[] targetFields, Object result) throws NoSuchFieldException, IllegalAccessException {
        if (logger.isDebugEnabled()) {
            logger.debug("process by default method, target field is [{}]", Arrays.toString(targetFields));
        }
        if (targetFields == null || targetFields.length == 0) {
            logger.debug("target fields is empty and will process target Object itself");
            // 如果没有传入指定字段,则默认扫描传入对象本身
            this.prepareValues(result.getClass(), result);
            // 本地缓存中获取
            this.processOne(result);
        } else {
            // 否则遍历指定字段
            Object value = new Object();
            for (String targetField : targetFields) {
                if (StringUtils.isEmpty(targetField)) {
                    logger.debug("target fields is empty and will process target Object itself");
                    // 如果自定字段中包含空字符串(""或null),则扫描传入对象本身
                    this.prepareValues(result.getClass(), result);
                    this.processOne(result);
                } else {
                    // 否则扫描指定字段所对应的对象
                    // 按.分隔,分段解析
                    int indexOfSplitor = targetField.indexOf(".");
                    Class<?> clazz = result.getClass();
                    Field field;
                    //Object value;

                    List<T> list = new ArrayList<>();
                    if (indexOfSplitor > 0) {
                        if (logger.isDebugEnabled()) {
                            logger.debug("recursive process result.{}", targetField.substring(0, indexOfSplitor));
                        }
                        field = this.getField(clazz, targetField.substring(0, indexOfSplitor));
                        field.setAccessible(true);
                        value = field.get(result);
                        this.process(new String[]{targetField.substring(indexOfSplitor + 1)}, value);
                    } else {
                        if ("data".equals(targetField)) {
                            // 处理头行关系中的头，即最外面的data指向的对象
                            logger.debug("recursive process result.{}", targetField);
                            // 获得封装的data对象
                            field = this.getField(clazz, targetField);
                            field.setAccessible(true);
                            value = field.get(result);

                            // value为反射得到的data属性里面的封装对象
                            this.process(null, value);
                        }
                    }
                    // 处理头行关系中行集合对象中的映射
                    if (!"data".equals(targetField)) {
                        Class<?> aClass = value.getClass();
                        field = this.getField(aClass, targetField);
                        field.setAccessible(true);
                        list = (List<T>) field.get(value);
                        this.process(null, list);
                    }

                }
            }
        }
    }

    /**
     * 直接处理当前对象
     *
     * @param obj 当前对象
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    private void processOne(Object obj) throws IllegalAccessException {
        if (obj == null) {
            return;
        }
        // 声明各种变量
        Class<?> clazz = obj.getClass();
        Field[] fields = FieldUtils.getAllFields(clazz);
        Map<String, LovDTO> localLovMap = this.getLocalLovMaps();
        Map<String, Map<String, String>> localLovValueMap = this.getLocalValueMaps();
        Map<String, String> innerLovValueMap;
        String meaning;
        Field meaningField;
        Object value;
        String lovCode;
        JsonNode jsonNode;
        // 循环处理对象中的所有字段
        for (Field field : fields) {
            // 找到class中所有被@LovValue注解的字段
            LovValue lovValueAnnotation = AnnotationUtils.getAnnotation(field, LovValue.class);
            if (lovValueAnnotation == null) {
                continue;
            }
            // 准备数据
            lovCode = lovValueAnnotation.lovCode();
            Assert.isTrue(StringUtils.isNotEmpty(lovCode), String.format("{%s} should not be null!", "lov code"));
            field.setAccessible(true);
            value = field.get(obj);
            if (value == null) {
                // 待翻译对象为null,跳过
                logger.debug("field value [{}] is null, skip...", field.getName());
                continue;
            }
            LovDTO lov = localLovMap.get(lovCode);
            if (lov == null) {
                logger.debug("invalid lov define [{}]", lovCode);
                // 无效的值集头
                meaning = null;
            } else {
                innerLovValueMap = localLovValueMap.get(lovCode);
                logger.debug("translating IDP lov values [{}]", lovCode);
                // 获取meaning
                if (MapUtils.isEmpty(innerLovValueMap)) {
                    logger.debug("lov values [{}] local cache is missing while translate, skip...", lovCode);
                    meaning = null;
                } else {
                    // 如果本地缓存中有该值集的有效缓存,则直接进行映射
                    meaning = innerLovValueMap.get(String.valueOf(value));
                }
            }
            // 将meaning回写到对象中
            String meaningFieldName = lovValueAnnotation.meaningField();
            try {
                if (StringUtils.isEmpty(meaningFieldName)) {
                    logger.debug("no meaning field name is assigned in field [{}], use default meaning field name", field.getName());
                    // 如果没有指定meaning字段名,则使用默认映射
                    meaningField = this.getField(clazz, field.getName().replaceAll("Code$", StringUtils.EMPTY) + "Meaning");
                } else {
                    logger.debug("using target field name [{}]", meaningFieldName);
                    // 如果指定了有效的meaning字段名,则将meaning写入该字段中
                    meaningField = this.getField(clazz, meaningFieldName);
                }
                meaningField.setAccessible(true);
            } catch (NoSuchFieldException | SecurityException e) {
                // 如果指定了的meaning字段名无效,跳过执行
                if (logger.isWarnEnabled()) {
                    // 改成占位符操作
                    logger.warn(String.format("field not found {%s} in class {%s} when process lov value", meaningFieldName, clazz == null ? "Unknown Class" : clazz.getSimpleName()));
                }
                continue;
            }
            // 没有找到有效的meaning时
            // -- 如果@LovValue指定了默认值,则使用该默认值
            // -- 否则使用value的原值
            if (meaning == null) {
                logger.warn("can not get any translate result by lov code [{}] and value [{}], do fallback process", lovCode, value);
                meaning = StringUtils.isEmpty(lovValueAnnotation.defaultMeaning()) ? String.valueOf(value) : lovValueAnnotation.defaultMeaning();
            }
            logger.debug("field lov value [{}] translate result is [{}]", lovCode, meaning);
            meaningField.set(obj, meaning);
        }

    }

    /**
     * 解析目标对象中的{@link LovValue}注解,将值集值缓存到ThreadLocal中
     *
     * @param clazz  待解析对象的class
     * @param originData
     */
    private void prepareValues(Class<?> clazz, Object originData) throws NoSuchFieldException, IllegalAccessException {
        logger.debug("process lov values...");
        // 各种变量的声明
        Field[] fields = FieldUtils.getAllFields(clazz);
        Map<String, LovDTO> localLovMap = this.getLocalLovMaps();
        Map<String, Map<String, String>> localValueMap = this.getLocalValueMaps();
        LovDTO lov;
        Field valueField;
        String valueFieldName;
        Object exampleOriginData;
        Class<?> originDataClazz;
        // 循环读取类中所有字段
        for (Field field : fields) {
            // 没有被@lovValue注解修饰的字段略过
            LovValue lovValue = AnnotationUtils.getAnnotation(field, LovValue.class);
            if (lovValue == null) {
                continue;
            }
            String lovCode = lovValue.lovCode();
            Assert.isTrue(StringUtils.isNotEmpty(lovCode), String.format("para not be null", "lov code"));
            logger.debug("target field id [{}] and target lov code is [{}]", field.getName(), lovCode);
            // 根据值集Code获取值集
            lov = localLovMap.get(lovCode);
            if (lov == null) {
                logger.debug("local lov [{}] define cache missing, query lov adapter...", lovCode);
                lov = this.lovAdapter.queryLovInfo(lovCode);
                if (lov == null) {
                    // 登记的值集信息无效则跳过
                    logger.warn("can not get lov define by code [{}] from local cache and lov adapter", lovCode);
                    continue;
                }
                logger.debug("get lov [{}] from lov adapter: [{}] and it will be cached in local thread", lovCode, lov);
                localLovMap.put(lovCode, lov);
            }
            // 检查值集类型
            if (!(BaConstants.LovTypes.IDP.equals(lov.getLovTypeCode()) || BaConstants.LovTypes.SQL.equals(lov.getLovTypeCode()))) {
                // 非独立值集和SQL值集不处理后续，跳过
                logger.debug("lov [{}] is not IDP or SQL,skip prepare...", lovCode);
                continue;
            }
            // 校验值集类型
            // 线程缓存中已有该值集，跳过
            if (localValueMap.get(lovCode) != null) {
                logger.debug("get values [{}] in local cache: [{}]", lovCode, localValueMap.get(lovCode));
                continue;
            }
            logger.debug("can not get values [{}] from local cache,query lov adapter...",lovCode);
            if(BaConstants.LovTypes.IDP.equals(lov.getLovTypeCode())){
                // 独立值集后续处理
                logger.debug("lov [{}] is IDP...", lovCode);
                List<LovValueDTO> values = this.lovAdapter.queryLovValue(lovCode);
                // 无效的值集,跳过
                if (CollectionUtils.isEmpty(values)) {
                    logger.warn("can not get lov values by code [{}] from local cache and lov adapter", lovCode);
                    continue;
                }
                Map<String, String> valueMeaningMap = new HashMap<>(values.size());
                values.forEach(value -> valueMeaningMap.put(value.getValue(), value.getMeaning()));
                logger.debug("get lov values [{}] from lov adapter: [{}] and it will be cached in local thread", lovCode, valueMeaningMap);
                localValueMap.put(lovCode, valueMeaningMap);
            }else if(BaConstants.LovTypes.SQL.equals(lov.getLovTypeCode())){
                // SQL值集后续处理
                logger.debug("lov [{}] is SQL...", lovCode);
                if(originData == null){
                    logger.debug("can not process SQL values [{}] when input data is null",lovCode);
                    continue;
                }
                //获取ID串
                List<Object> identities;
                if(originData instanceof Collection){
                    logger.debug("input data is Collection...");
                    Collection<?> originDatas = (Collection<?>) originData;
                    if(originDatas.isEmpty()){
                        logger.debug("can not process SQL values [{}] when input data collection is empty", lovCode);
                        continue;
                    }
                    identities = new ArrayList<>(originDatas.size());
                    valueFieldName = lov.getValueField();
                    if(StringUtils.isEmpty(valueFieldName)){
                        logger.debug("can not process SQL values [{}] when input data's valueField name is null", lovCode);
                        continue;
                    }
                    exampleOriginData = originDatas.stream().filter(Objects::nonNull).findFirst().orElse(null);
                    if (exampleOriginData == null) {
                        logger.debug("can not process SQL values [{}] when all data is null in input collection",lovCode);
                        continue;
                    }
                    originDataClazz = exampleOriginData.getClass();
                    valueField = originDataClazz.getDeclaredField(lov.getValueField());
                    if (valueField == null) {
                        logger.debug("can not process values [{}] when input data's valueField is null",lovCode);
                        continue;
                    }
                    valueField.setAccessible(true);
                    for (Object item : originDatas) {
                        identities.add(valueField.get(item));

                    }
                }else{
                    logger.debug("input data is not Collection...");
                    valueFieldName = lov.getValueField();
                    if(StringUtils.isEmpty(valueFieldName)){
                        logger.debug("can not process SQL values [{}] when input data's valueFieldName is null", lovCode);
                        continue;
                    }
                    identities = new ArrayList<>(1);
                    valueField = originData.getClass().getDeclaredField(valueFieldName);
                    if (valueField == null) {
                        logger.debug("can not process values [{}] when input data's valueField is null",lovCode);
                        continue;
                    }
                    valueField.setAccessible(true);
                    identities.add(valueField.get(originData));
                }

                // TODO 远程调用查询SQL值集


            }

        }
    }

    /**
     * 获得给定类及其所有父类中指定的字段
     *
     * @param clazz
     * @param fieldName
     * @return
     * @throws NoSuchFieldException
     */
    private Field getField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        Field[] fields = FieldUtils.getAllFields(clazz);
        for (Field field : fields) {
            if (Objects.equals(fieldName, field.getName())) {
                return field;
            }
        }
        throw new NoSuchFieldException(fieldName);
    }


    /**
     * 从ThreadLocal中加载本线程的Map数据,如果没有数据则创建初始化Map数据
     *
     * @return 本线程的数据
     */
    private Map<String, Map<String, String>> getLocalValueMaps() {
        Map<String, Map<String, String>> localValueMaps = this.valueMaps.get();
        if (localValueMaps == null) {
            localValueMaps = new TTLMap.Builder<String, Map<String, String>>().ttl(1L).build();
            this.valueMaps.set(localValueMaps);
        }
        return localValueMaps;
    }

    /**
     * 从ThreadLocal中加载本线程的Map数据,如果没有数据则创建初始化Map数据
     *
     * @return 本线程的数据
     */
    private Map<String, LovDTO> getLocalLovMaps() {
        Map<String, LovDTO> localLovMaps = this.lovMaps.get();
        if (localLovMaps == null) {
            localLovMaps = new TTLMap.Builder<String, LovDTO>().ttl(1L).build();
            this.lovMaps.set(localLovMaps);
        }
        return localLovMaps;
    }
}
