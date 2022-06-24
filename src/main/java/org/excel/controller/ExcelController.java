package org.excel.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ExcelController {
	
	@GetMapping("/excel")
	public String excel() {
		
		System.out.println("==== excel ===");
		return "excel";
	}
	
	@PostMapping("/uploadFormAction")
	public String uploadFormPost(MultipartFile[] uploadFile, Model model) {
		String uploadFolder = "c:\\upload";
		for(MultipartFile multipartFile : uploadFile) {
			File savefile = new File(uploadFolder, multipartFile.getOriginalFilename());
			try {
				multipartFile.transferTo(savefile);
				
				try {
		            FileInputStream file = new FileInputStream("c:\\upload\\test.xlsx");
		            XSSFWorkbook workbook = new XSSFWorkbook(file);
		 
		            int rowindex=0;
		            int columnindex=0;
		            //시트 수 (첫번째에만 존재하므로 0을 준다)
		            //만약 각 시트를 읽기위해서는 FOR문을 한번더 돌려준다
		            XSSFSheet sheet=workbook.getSheetAt(0);
		            //행의 수
		            int rows=sheet.getPhysicalNumberOfRows();
		            for(rowindex=0;rowindex<rows;rowindex++){
		                //행을읽는다
		                XSSFRow row=sheet.getRow(rowindex);
		                if(row !=null){
		                    //셀의 수
		                    int cells=row.getPhysicalNumberOfCells();
		                    for(columnindex=0; columnindex<=cells; columnindex++){
		                        //셀값을 읽는다
		                        XSSFCell cell=row.getCell(columnindex);
		                        String value="";
		                        //셀이 빈값일경우를 위한 널체크
		                        if(cell==null){
		                            continue;
		                        }else{
		                            //타입별로 내용 읽기
		                            switch (cell.getCellType()){
		                            case XSSFCell.CELL_TYPE_FORMULA:
		                                value=cell.getCellFormula();
		                                break;
		                            case XSSFCell.CELL_TYPE_NUMERIC:
		                                value=cell.getNumericCellValue()+"";		
		                                break;
		                            case XSSFCell.CELL_TYPE_STRING:
		                                value=cell.getStringCellValue()+"";			
		                                break;
		                            case XSSFCell.CELL_TYPE_BLANK:
		                                value=cell.getBooleanCellValue()+"";
		                                break;
		                            case XSSFCell.CELL_TYPE_ERROR:
		                                value=cell.getErrorCellValue()+"";
		                                break;
		                            }
		                        }
		                      
		                        System.out.println(rowindex+"번 행 : "+columnindex+"번 열 값은: "+value);
		                    }
		 
		                }
		            }
		 
		        }catch(Exception e) {
		            e.printStackTrace();
		        }
		
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		return "list";
	}
	
	

}
