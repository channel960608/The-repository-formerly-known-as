package edu.neu.coe.huskySort.sort.msdSort;

/**
 * @author Caspar
 * @date 2021/12/2 04:39
 */
public interface StringMsdTranslator extends UnicodeTranslator{

    /**
     * @return the name of this coder.
     */
    default String name() {
        return "StringMsdTranslator";
    }

    /**
     * Decode unicode symbols into letters.
     * As much as possible, if x > y, huskyEncode(x) > huskyEncode(y).
     * If this cannot be guaranteed, then the result of imperfect(z) will be true.
     *
     * @param str the String to encode.
     * @return the result String, composed of letters.
     */
    String msdDecode(String str);

    /**
     * Decode unicode symbols in batch.
     * As much as possible, if x > y, huskyEncode(x) > huskyEncode(y).
     * If this cannot be guaranteed, then the result of imperfect(z) will be true.
     *
     * @param strs the String Array to decode.
     * @return the result String Array, composed of letters.
     */
    String[]  batchDecode(String[] strs);

    /**
     * In order to do translate, we have to make sure the input characters meet the requirements.
     *
     * @param c the Character to determine.
     * @return boolean result.
     */
    boolean detectCharacter(char c);

}
