package com.ling.other.modules.fileupload;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author zhangling
 * @since 2020/9/25 15:36
 */
@RestController
public class FileUploadController {

    SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");


    @PostMapping("/upload")
    public Map<String, Object> fileupload(MultipartFile file, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        String originalFilename = file.getOriginalFilename();
        /*if (!originalFilename.endsWith(".pdf")) {
            result.put("status", "error");
            result.put("msg", "文件类型不对");
            return result;
        }*/

        String format = sdf.format(new Date());
        // 获取的是临时路径，项目重启就会消息
        String realPath = request.getServletContext().getRealPath("/") + format;
        File folder = new File(realPath);
        if (!folder.exists()) {
            folder.mkdirs();
            System.out.println(folder.toString());
        }
        String newName = UUID.randomUUID().toString() + ".pdf";
        try {
            file.transferTo(new File(folder, newName));
            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + format + newName;
            result.put("status", "success");
            result.put("name",originalFilename);
            result.put("url", url);
        } catch (IOException e) {
            e.printStackTrace();
            result.put("status", "error");
            result.put("msg", e.getMessage());
        }
        return result;
    }

    @PostMapping("/upload2")
    public Map<String,Object> fileupload2(MultipartFile file, HttpServletRequest req) {
        Map<String, Object> result = new HashMap<>();
        String format = sdf.format(new Date());
        String realPath = req.getServletContext().getRealPath("/") + format;
        File folder = new File(realPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String oldName = file.getOriginalFilename();
        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."));
        try {
            file.transferTo(new File(folder, newName));
            String url = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + format + newName;
            result.put("status", "OK");
            result.put("name", oldName);
            result.put("url", url);
        } catch (IOException e) {
            result.put("status", "ERROR");
            result.put("msg", e.getMessage());
        }
        return result;
    }
}
