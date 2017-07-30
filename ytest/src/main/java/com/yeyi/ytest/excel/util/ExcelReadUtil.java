package com.yeyi.ytest.excel.util;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 通过流对excel文件的读取操作
 * 
 * @author hsjing
 */
public class ExcelReadUtil
{

    /**
     * excel内容读取
     * 
     * @param xssfWorkbook
     *            2007版本
     * @return
     * @throws IOException
     */
    @SuppressWarnings("unused")
    public Map<String, List<List<String>>> readExcelXlsx(
            XSSFWorkbook xssfWorkbook) throws IOException
    {
        Map<String, List<List<String>>> map = new HashMap<String, List<List<String>>>();

        // 读取excel的所有表
        for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++)
        {

            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);

            // excel的表名
            String mapkey = xssfSheet.getSheetName();

            // 一张表一个大list集合
            List<List<String>> listKey = new ArrayList<List<String>>();
            if (xssfSheet == null)
            {
                continue;
            }

            // 读取excel的所有行
            for (int rowNum = 0; rowNum < xssfSheet.getLastRowNum(); rowNum++)
            {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);

                // 一表的一行一个小list集合
                List<String> list = new ArrayList<String>();

                for (int colNum = 0; colNum < xssfRow
                        .getPhysicalNumberOfCells(); colNum++)
                {
                    // 每一列一个string,
                    String cellVal = getValue(xssfRow.getCell(colNum));

                    // 保存某行某列
                    list.add(cellVal);
                }

                // 保存某行整列
                listKey.add(list);
            }

            // 保存一个表
            map.put(mapkey, listKey);
        }

        // 返回整个excel的所有表
        return map;
    }

    /**
     * 内部excel表的单元给内容读取
     * 
     * @param xssfRow
     *            2007的版本
     * @return
     */
    @SuppressWarnings("static-access")
    private String getValue(XSSFCell xssfRow)
    {
        try
        {
            if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN)
            {
                return String.valueOf(xssfRow.getBooleanCellValue());
            } else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC)
            {
                return String.valueOf(xssfRow.getNumericCellValue());
            } else
            {
                return String.valueOf(xssfRow.getStringCellValue());
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 内部excel表的单元给内容读取
     * 
     * @param hssfCell
     *            2003版本
     * @return
     */
    @SuppressWarnings("static-access")
    private String getValue(HSSFCell hssfCell)
    {
        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN)
        {
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC)
        {
            return String.valueOf(hssfCell.getNumericCellValue());
        } else
        {
            return String.valueOf(hssfCell.getStringCellValue());
        }
    }

    /**
     * excel内容读取
     * 
     * @param hssfWorkbook
     *            2003版本
     * @return
     * @throws IOException
     */
    @SuppressWarnings("unused")
    public Map<String, List<List<String>>> readExcelXls(
            HSSFWorkbook hssfWorkbook) throws IOException
    {
        Map<String, List<List<String>>> map = new HashMap<String, List<List<String>>>();
        // InputStream is = new FileInputStream(path);
        // HSSFWorkbook hssfWorkbook = (HSSFWorkbook)Workbook;
        // Read the Sheet
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++)
        {
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            String mapkey = hssfSheet.getSheetName();
            List<List<String>> listKey = new ArrayList<List<String>>();
            if (hssfSheet == null)
            {
                continue;
            }

            // Read the Row
            for (int rowNum = 1; rowNum < hssfSheet.getLastRowNum(); rowNum++)
            {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                List<String> list = new ArrayList<String>();
                if (hssfRow != null)
                {
                    // 每一列一个string,
                    String cellVal = getValue(hssfRow.getCell(0));

                    // 保存某行某列
                    list.add(cellVal);
                }
                listKey.add(list);
            }
            map.put(mapkey, listKey);
        }
        return map;
    }

    /**
     * Excel转化成javabean<br/>
     * Excel单元格类型为字符 数字 公式
     * 
     * @param workbook
     *            Excel文档
     * @param sheetName
     *            sheet名
     * @param colNameMap
     *            javabean的属性(不区分大小写)和Excel列名关联 key：javabean的属性，value：Excel列名
     * @param clazz
     *            javabean
     * @return
     * @throws Exception
     */

    public static <T> List<T> excelToBean(Workbook workbook,
            String sheetName, Map<String, String> colNameMap, Class<T> clazz)
            throws Exception
    {
        List<T> list = new ArrayList<T>();

        Sheet sheet = workbook.getSheet(sheetName);
        // sheet名不存在
        if (sheet == null)
        {
            return null;
        }

        // 总行数
        int rows = sheet.getLastRowNum();
        // 第一行：列名
        Row firstRow = sheet.getRow(0);
        // 总列数
        int cols = firstRow.getPhysicalNumberOfCells();

        Method[] methods = clazz.getMethods();
        // set方法和列番号
        Map<String, Method> colNumberAndMethodName = new LinkedHashMap<String, Method>();

        // set方法和列番号配对 查找和value对应的列番号和key对应的set方法名
        for (Map.Entry<String, String> entry : colNameMap.entrySet())
        {

            for (int i = 0; i < cols; i++)
            {
                String colName = firstRow.getCell(i).getStringCellValue();

                // 找到对应的列名
                if (entry.getValue().equals(colName))
                {
                    for (Method method : methods)
                    {
                        String methodName = method.getName();

                        // set方法
                        if (methodName.startsWith("set"))
                        {
                            String key = methodName.substring(3);

                            // 找到对应的方法名
                            if (key.equalsIgnoreCase(entry.getKey()))
                            {
                                // key： 方法名 value： 列番号
                                colNumberAndMethodName.put(String.valueOf(i),
                                        method);
                                break;
                            }
                        }
                    }
                    break;
                }
            }
        }

        for (int i = 1; i <= rows; i++)
        {
            Row row = sheet.getRow(i);

            // 空行检验
            if (isBlankRow(row))
            {
                continue;
            }

            T javaBeanT = clazz.newInstance();

            for (Map.Entry<String, Method> entry : colNumberAndMethodName
                    .entrySet())
            {

                Cell cell = row
                        .getCell(Integer.parseInt(entry.getKey()));

                Method method = entry.getValue();
                
                @SuppressWarnings("rawtypes")
                Class[] paramTypes = method.getParameterTypes();

                // 整形
                if (paramTypes[0].getName().equals("int"))
                {
                    int intvalue = (int) cell.getNumericCellValue();
                    method.invoke(javaBeanT, intvalue);

                    // 浮点型
                } else if (paramTypes[0].getName().equals("float"))
                {
//                    float floatvalue = (float) cell.getNumericCellValue();
//                    method.invoke(javaBeanT, floatvalue);
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    String stringVaule = cell.getStringCellValue();
                    float floatvalue =0;
                    if(stringVaule!=null)
                    {
                    	floatvalue=Float.parseFloat(stringVaule);
                    }
                    method.invoke(javaBeanT, floatvalue);

                    // 布尔类型
                } else if (paramTypes[0].getName().equals("boolean"))
                {
                    Boolean booleanVaule = cell.getBooleanCellValue();
                    method.invoke(javaBeanT, String.valueOf(booleanVaule));

                    // 字符串类型
                } else if (paramTypes[0].getName().equals("java.lang.String"))
                {
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    String stringVaule = cell.getStringCellValue();

                    method.invoke(javaBeanT, stringVaule.trim());
                }

            }

            list.add(javaBeanT);
        }

        return list;
    }

    public synchronized static List<Map<String, Object>> parseExcel(Workbook workbook,
            String sheetName, Map<String, String> colNameMap)
    {

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Sheet sheet = workbook.getSheet(sheetName);
        // sheet名不存在
        if (sheet == null)
        {
            return null;
        }

        // 总行数
        int rows = sheet.getLastRowNum();
        // 第一行：列名
        Row firstRow = sheet.getRow(0);
        // 总列数
        int cols = firstRow.getPhysicalNumberOfCells();

        // set方法和列番号
        Map<Integer, String> colNumberAndKeyName = new LinkedHashMap<Integer, String>();

        for (Map.Entry<String, String> entry : colNameMap.entrySet())
        {

            entry.getValue();

            for (int i = 0; i < cols; i++)
            {
                firstRow.getCell(i);
                String colName = firstRow.getCell(i).getStringCellValue();
                if (entry.getValue().equals(colName))
                {
                    colNumberAndKeyName.put(i, entry.getKey());
                }
            }
        }

        // 获取数据
        for (int i = 1; i <= rows; i++)
        {
            Row row = sheet.getRow(i);
            if (row == null || isBlankRow(row))
            {
                continue;
            }

            Map<String, Object> dataMap = new HashMap<String, Object>();

            for (Entry<Integer, String> entry : colNumberAndKeyName.entrySet())
            {
                Object value = getValue(row.getCell(entry.getKey()));
                dataMap.put(entry.getValue(), value);
            }

            list.add(dataMap);
        }
        return list;
    }

    public static Object getValue(Cell cell)
    {
        if (cell == null)
        {
            return "";
        }
        int celType = cell.getCellType();
        switch (celType)
        {
        // 字符串类型
        case XSSFCell.CELL_TYPE_STRING:
            String stringVaule = cell.getStringCellValue();
            return stringVaule.trim();
            // 数字
        case XSSFCell.CELL_TYPE_NUMERIC:
            return cell.getNumericCellValue();
            // 布尔类型
        case XSSFCell.CELL_TYPE_BOOLEAN:
            boolean booleanVaule = cell.getBooleanCellValue();
            return booleanVaule;

            // 空值
        case XSSFCell.CELL_TYPE_BLANK:
            return "";

            // 计算公式
        case XSSFCell.CELL_TYPE_FORMULA:
            String formulaVaule = cell.getCellFormula();
            return formulaVaule;
        default:
            break;
        }

        return "";
    }

    /**
     * 判断行是否为空行
     * 
     * @param row
     * @return
     */
    public static boolean isBlankRow(Row row)
    {
        if (row == null)
            return true;
        boolean result = true;
        for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++)
        {
            Cell cell = row.getCell(i, HSSFRow.RETURN_BLANK_AS_NULL);
            String value = "";
            if (cell != null)
            {
                switch (cell.getCellType())
                {
                case Cell.CELL_TYPE_STRING:
                    value = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    value = String.valueOf((int) cell.getNumericCellValue());
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    value = String.valueOf(cell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    value = String.valueOf(cell.getCellFormula());
                    break;
                // case Cell.CELL_TYPE_BLANK:
                // break;
                default:
                    break;
                }

                if (!value.trim().equals(""))
                {
                    result = false;
                    break;
                }
            }
        }

        return result;
    }

    public static List<Map<String, Object>> parseExcel(Workbook workbook,
            Map<String, String> colNameMap, SheetConfig config)
    {
        
        if(config==null){
            config=new SheetConfig();
        }
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        
        Sheet sheet = workbook.getSheetAt(config.getSheetIndex());
        
        // sheet名不存在
        if (sheet == null)
        {
            return null;
        }
        
        
        // 总行数
        int rows = sheet.getLastRowNum();
        
        // 第一行：列名
        Row firstRow = sheet.getRow(config.getStartRow());
        
        //数据起始行
        int dataRowIndex=config.getStartRow()+1;

        // 总列数
        int cols = firstRow.getLastCellNum();

        // set方法和列番号
        Map<Integer, String> colNumberAndKeyName = new LinkedHashMap<Integer, String>();

        for (Map.Entry<String, String> entry : colNameMap.entrySet())
        {

//            entry.getValue();
            
            for (int i = 0; i < cols; i++)
            {
                firstRow.getCell(i);
                Cell cell =firstRow.getCell(i);
                String colName = "";
                if(cell!=null){
                    colName= cell.getStringCellValue();
                }
                if (entry.getValue().equals(colName))
                {
                    colNumberAndKeyName.put(i, entry.getKey());
                }
            }
        }

        // 获取数据
        for (int i = dataRowIndex; i <= rows; i++)
        {
            Row row = sheet.getRow(i);
            if (row == null || isBlankRow(row))
            {
                continue;
            }

            Map<String, Object> dataMap = new HashMap<String, Object>();

            for (Entry<Integer, String> entry : colNumberAndKeyName.entrySet())
            {
                Object value = getValue(row.getCell(entry.getKey()));
                dataMap.put(entry.getValue(), value);
            }

            list.add(dataMap);
        }
        return list;
    }

}
