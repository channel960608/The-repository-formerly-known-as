package edu.neu.coe.huskySort.sort.msdSort;

import edu.neu.coe.huskySort.util.LazyLogger;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * @author Caspar
 * @date 2021/11/27 16:18
 */
public class ChineseTranslator implements MsdTranslator<String, String>{

    private final static LazyLogger logger = new LazyLogger(ChineseTranslator.class);

    private final String TRANSLATOR_NAME = "ChineseTranslator";

    private static HanyuPinyinOutputFormat format = initFormat();

    private static HanyuPinyinOutputFormat initFormat() {
        if (null == format) {
            HanyuPinyinOutputFormat mFormat = new HanyuPinyinOutputFormat();
            mFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            mFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);// 小写
            mFormat.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);
            return mFormat;
        }
        return format;
    }

    @Override
    public String name() {
        return TRANSLATOR_NAME;
    }

    @Override
    public String msdDecode(String str) {
        char[] input = StringUtils.trim(str).toCharArray();
        StringBuilder sb = new StringBuilder();
        try {
            for (char ch : input) {
                if (detectCharacter(ch)) {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(ch, format);
                    sb.append(temp[0]);
                } else
                    sb.append(ch);
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            logger.error("string to latin pinyin error", e);
        }
        return sb.toString();
    }

    @Override
    public String[] batchDecode(String[] strs) {
        return Arrays.stream(strs).map(this::msdDecode).toArray(String[]::new);
    }

    @Override
    public boolean detectCharacter(char c) {
        return Character.toString(c).matches("[\\u4E00-\\u9FA5]+");
    }

}
