package ua.com.curex.web;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/excel")
public class ExcelContoller {
	@RequestMapping(method = RequestMethod.POST)
    public @ResponseBody String excel(String excel, String extension) throws IOException {
        if (extension.equals("csv") || extension.equals("xml")) {
        	
        	
            String filename = "export."+extension; // "export.xls"; для режима Эксель
            File file = new File(filename);
            
            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            //FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedOutputStream bs = new BufferedOutputStream(new FileOutputStream(
            		file.getAbsoluteFile()));
            //BufferedWriter bw = new BufferedWriter(fw);
            //int endIndex = excel.indexOf("</Workbook>");
            //bw.write(excel.substring(0, endIndex+11));
            //bw.write(excel.replace(",","\t"));
            bs.write(excel.getBytes(Charset.forName("utf-8")));
            bs.close();
            return filename;
        } else {
            return "";
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public void excel(String filename, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition",
            "attachment;filename="+filename);
        
        File file = new File(filename);
        
        FileInputStream fileIn = new FileInputStream(file);
        ServletOutputStream out = response.getOutputStream();

        byte[] outputByte = new byte[1024];
        int bytesRead;
        while ((bytesRead = fileIn.read(outputByte)) > 0) {
        	out.write(outputByte, 0, bytesRead);
        }
        /*
        while (fileIn.read(outputByte, 0, 1024) != -1) {
            out.write(outputByte, 0, 1024);
        }*/
        fileIn.close();
        out.flush();
        out.close();
    }
}
