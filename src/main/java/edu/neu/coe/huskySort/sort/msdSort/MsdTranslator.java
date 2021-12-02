package edu.neu.coe.huskySort.sort.msdSort;/**
* @author Caspar
* @date 2021/11/27 15:12
*/

/**
 * This interface models the process of translating specific language coded in Unicode into letters.
 * Different languages exist in different cultures. We have unicode to present most of the symbols in all languages
 * in the world. And also, we must be able to index each word using 26 letters when you try to type in these words
 * using a keyboard.
 * For example, Chinese can be presented using Pinyin, and Japanese can be presented using Romanization.
 * Keep in mind that the process is not reversible, since it is a many-to-1 mapping.
 *
 */
public interface MsdTranslator<I extends CharSequence, O extends CharSequence> {

    /**
     * @return the name of this coder.
     */
    default String name() {
        return "CharConsequenceMsdTranslator";
    }

    /**
     * Decode unicode symbols into letters.
     * As much as possible, if x > y, huskyEncode(x) > huskyEncode(y).
     * If this cannot be guaranteed, then the result of imperfect(z) will be true.
     *
     * @param str the String to encode.
     * @return the result String, composed of letters.
     */
    O msdDecode(I str);

    /**
     * Decode unicode symbols in batch.
     * As much as possible, if x > y, huskyEncode(x) > huskyEncode(y).
     * If this cannot be guaranteed, then the result of imperfect(z) will be true.
     *
     * @param strs the String Array to decode.
     * @return the result String Array, composed of letters.
     */
    O[]  batchDecode(I[] strs);

    /**
     * In order to do translate, we have to make sure the input characters meet the requirements.
     *
     * @param c the Character to determine.
     * @return boolean result.
     */
    boolean detectCharacter(char c);

}
