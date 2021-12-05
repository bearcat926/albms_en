package alb.common.utils;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @Date: 2020/7/8 15:35
 * @Description:fileconvertMultipartFile
 */
public class FileToMultipartFile {

    private static void picUpload(MultipartFile file) {
        //Image upload
        if (file != null) {// Check whether the uploaded file is empty
            String path = null;// The file path
            String type = null;// The file type
            String fileName = file.getOriginalFilename();// Original file name
            System.out.println("The original name of the uploaded file:" + fileName);
            // Determine the file type
            type = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".") + 1) : null;
            if (type != null) {// Check whether the file type is empty
                if ("GIF".equalsIgnoreCase(type) || "PNG".equalsIgnoreCase(type) || "JPG".equalsIgnoreCase(type)) {
                    // The root path where the project actually publishes runs in the container
                    //String realPath=request.getSession().getServletContext().getRealPath("/");
                    //Brother wrote his own path
                    String realPath = "F://pic//";
                    // User-defined file name
                    String trueFileName = fileName;
                    // Set the path for storing image files
                    path = realPath + trueFileName;
                    System.out.println("The path to the image file:" + path);
                    // Saves the file to the specified path
                    try {
                        file.transferTo(new File(path));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("The file is successfully uploaded to the specified directory");
                } else {
                    System.out.println("Not the type of file we want,Please re-upload as required");
                }
            } else {
                System.out.println("The file type is empty");
            }
        }
        System.out.println("No corresponding file was found");
    }

    public static MultipartFile getMulFileByPath(String picPath) {
        FileItem fileItem = createFileItem(picPath);
        MultipartFile mfile = new CommonsMultipartFile(fileItem);
        return mfile;
    }

    private static FileItem createFileItem(String filePath) {
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        String textFieldName = "textField";
        int num = filePath.lastIndexOf(".");
        String extFile = filePath.substring(num);
        FileItem item = factory.createItem(textFieldName, "text/plain", true,
                "MyFileName" + extFile);
        File newfile = new File(filePath);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        try {
            FileInputStream fis = new FileInputStream(newfile);
            OutputStream os = item.getOutputStream();
            while ((bytesRead = fis.read(buffer, 0, 8192))
                    != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return item;
    }

}
