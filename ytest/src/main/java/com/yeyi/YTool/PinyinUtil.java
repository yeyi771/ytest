package com.yeyi.YTool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinUtil {

	private static final Logger LOG = LoggerFactory.getLogger(PinyinUtil.class);

	public static String getPinYin(String src) {

		StringBuilder result = new StringBuilder();
		char[] charSrc = src.toCharArray();
		String[] arrSrc = new String[charSrc.length];
		// 设置汉字拼音输出的格式
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		format.setVCharType(HanyuPinyinVCharType.WITH_V);

		try {
			for (int i = 0; i < charSrc.length; i++) {
				// 判断是否为汉字字符
				if (Character.toString(charSrc[i])
						.matches("[\\u4E00-\\u9FA5]+")) {
					arrSrc = PinyinHelper.toHanyuPinyinStringArray(charSrc[i],
							format);// 将汉字的几种全拼都存到arrSrc数组中
					if (arrSrc != null && arrSrc.length != 0) {
						result.append(arrSrc[0]);// 取出该汉字全拼的第一种读音并连接到字符串result后
					}
				} else {
					// 如果不是汉字字符，直接取出字符并连接到字符串result后
					result.append(Character.toString(charSrc[i]));
				}
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			LOG.error("PinyinUtil.getPinYin()", e);
		}

		return result.toString();
	}

	/**
	 * @Description: 判断是否是中文开头
	 * @param: @param src
	 * @param: @return
	 * @return: boolean
	 * @throws
	 */
	public static boolean isChinese(String src) {

		boolean result = false;
		char[] charSrc = src.toCharArray();
		if (Character.toString(charSrc[0]).matches("[\\u4E00-\\u9FA5]+")) {
			result = true;
		}
		return result;
	}

	/**
	 * @Description: 返回字符中一个汉字
	 * @param: @param src
	 * @param: @return
	 * @return: String
	 * @throws
	 */
	public static String getChineseWord(String src) {
		String result = null;
		char[] charSrc = src.toCharArray();
		for (int i = 0; i < charSrc.length; i++) {
			// 判断是否为汉字字符
			if (Character.toString(charSrc[i]).matches("[\\u4E00-\\u9FA5]+")) {
				result = Character.toString(charSrc[i]);
				break;
			}
		}

		return result;
	}
}
