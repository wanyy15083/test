package com.test.utils;

import jxl.SheetSettings;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.write.*;

import java.io.File;

public class TestExcel {

	/**
	 * 生成excel表格基本为三个步骤： 1.创建excel工作簿 2.创建工作表sheet 3.创建单元格并添加到sheet中
	 * 
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		/*
		 * 创建可读写的excel对象，一个excel文件对应着一个WritableWorkbook对象
		 * 以下是通过生成本地文件的方式创建excel对象，还可以在构造时传入输出流的方式创建
		 * 注意：因为jxl不支持office2007及以上版本，所以只能操作.xls文件，而无法操作.xlsx文件
		 * 方法：Workbook.createWorkbook()
		 */
		WritableWorkbook book = Workbook
				.createWorkbook(new File("F:/test.xls"));
		// WritableWorkbook book = Workbook.createWorkbook(outputstream);

		/*
		 * 创建只读的excel对象
		 */
		// jxl.Workbook rw = jxl.Workbook.getWorkbook(new File(sourcefile));

		/*
		 * 创建工作表sheet对象，可以根据需求创建多个 方法：createSheet() pararm1：工作表的名称
		 * param2：工作表的索引下表（0开始）
		 */
		WritableSheet sheet = book.createSheet("工作表1", 0);
		// WritableSheet sheet2 = book.createSheet("工作表2", 1);

		/*
		 * 设置工作表的行或列冻结
		 */
		SheetSettings settings = sheet.getSettings();
		// 冻结前两行
		settings.setVerticalFreeze(1);
		// 冻结前两列
		// settings.setHorizontalFreeze(2);

		/*
		 * 添加文本单元格，一个Label对象对应着一个单元格 param1:第几列 param2:第几行 param3:单元格内容（值）
		 */
		Label label_00 = new Label(0, 0, "标题1");

		/*
		 * 设置单元格字体，还有很多其他样式，不一一列举
		 */
		WritableFont wf = new WritableFont(WritableFont.createFont("楷书"), 20);
		/*
		 * 设置单元格样式并添加字体样式
		 */
		WritableCellFormat wcf = new WritableCellFormat(wf);
		// 设置内容居中
		wcf.setAlignment(Alignment.CENTRE);
		// 设置单元格的背景颜色
		wcf.setBackground(jxl.format.Colour.RED);

		/*
		 * 在创建单元格的时候使用样式
		 */
		Label label_10 = new Label(1, 0, "标题2", wcf);

		/**
		 * 注意：要把所有单元格对象添加到工作表中
		 */
		sheet.addCell(label_00);
		sheet.addCell(label_10);

		/*
		 * 合并单元格 方法：sheet.mergeCells(x,y,m,n)，xymn都从0开始
		 * 表示将单元格从第(x+1)列、第(y+1)行合并到第(m+1)列、(n+1)行合并
		 */
		sheet.mergeCells(0, 1, 0, 2);

		/*
		 * 写入到文件
		 */
		book.write();
		/*
		 * 一定要使用close()方法来关闭先前创建的对象，以释放其所占用的内存空间
		 */
		book.close();

	}

//	/**
//	 * 读取excel文件基本步骤： 1.选取excel文件得到工作簿 2.选择工作表sheet 3.选择单元格cell 4.读取信息
//	 *
//	 * @throws IOException
//	 * @throws BiffException
//	 */
//	public static void main(String[] args) throws Exception {
//		/*
//		 * 选择excel文件，创建jxl.Workbook对象 方法：Workbook.getWorkbook()
//		 */
//		Workbook book = Workbook.getWorkbook(new File(
//				"C:\\Users\\admin\\Downloads\\20160513103014.xls"));
//
//		/*
//		 * 读取工作表 方法：getSheet...
//		 */
//		Sheet sheet = book.getSheet(0);// 指定下标方式
//		// Sheet sheet = book.getSheet("sheetname");//指定名称方式
//		// String[] sheets = book.getSheetNames();// 工作表名称数组
//		// Sheet[] sheets = book.getSheets();//工作表对象数组
//
//		/*
//		 * 读取单元格 方法：getCell param1：第几列 param2：第几行
//		 */
//		Cell cell = sheet.getCell(0, 2);
//
//		/*
//		 * 读取单元格的值 方法：getContents()
//		 */
//		String content = cell.getContents();
//
//		System.out.println("[debug]:" + content);
//
//		/*
//		 * 同样，可以获取到单元格的类型，返回的是CellType对象
//		 * 各种类型可以通过CellType.Type来调用，比如：CellType.LABEL
//		 */
//		CellType type = cell.getType();
//		if (type == CellType.LABEL) {
//			System.out.println("[debug]:这是文本单元格");
//		}
//
//		/**
//		 * 释放资源 当你完成对 Excel 电子表格数据的处理后，一定要使用 close()
//		 * 方法来关闭先前创建的对象，以释放读取数据表的过程中所占用的内存空间，在读取大量数据时显得尤为重要
//		 */
//		book.close();
//
//	}

//	/**
//	 * 导出EXCEL文件,主要做的就是查询一堆列表，然后创建多个工作表，每个工作表下面有很多标题及列表数据
//	 *
//	 * @param response
//	 */
//	@RequestMapping(value = "export")
//	public void export(HttpServletResponse response) {
//
//		// 文件名
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//		String fileName = sdf.format(new Date()) + ".xls";
//
//		response.setContentType("application/x-excel");
//		response.setCharacterEncoding("UTF-8");
//		response.addHeader("Content-Disposition", "attachment;filename="
//				+ fileName);// excel文件名
//
//		// 业务数据，不用关注，网友请假装有数据，哈哈哈...下同
//		List<DataItem> itemList = new ArrayList<DataItem>();
//
//		try {
//			// 1.创建excel文件
//			WritableWorkbook book = Workbook.createWorkbook(response
//					.getOutputStream());
//			// 居中
//			WritableCellFormat wf = new WritableCellFormat();
//			wf.setAlignment(Alignment.CENTRE);
//
//			WritableSheet sheet = null;
//			SheetSettings settings = null;
//			for (int i = 0; i < itemList.size(); i++) {
//				// 2.创建sheet并设置冻结前两行
//				sheet = book.createSheet(itemList.get(i).getName(), i);
//				settings = sheet.getSettings();
//				settings.setVerticalFreeze(2);
//				// 3.添加第一行及第二行标题数据
//				sheet.addCell(new Label(0, 0, "名称", wf));
//				sheet.addCell(new Label(1, 0, itemList.get(i).getName(), wf));
//				sheet.addCell(new Label(2, 0, "ID", wf));
//				sheet.addCell(new Label(3, 0, itemList.get(i).getId() + "", wf));
//				sheet.addCell(new Label(0, 1, "时间", wf));
//				sheet.addCell(new Label(1, 1, "值", wf));
//
//				// 4.历史数据，业务数据，不用关注
//				List<HisData> hisList = new ArrayList<HisData>();
//				if (hisList != null && hisList.size() > 0) {
//
//					// 5.将历史数据添加到单元格中
//					for (int j = 0; j < hisList.size(); j++) {
//						sheet.addCell(new Label(0, j + 2, hisList.get(j)
//								.getTime() + "", wf));
//						sheet.addCell(new Label(1, j + 2, hisList.get(j)
//								.getValue() + "", wf));
//					}
//				}
//			}
//			// 6.写入excel并关闭
//			book.write();
//			book.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}

}
