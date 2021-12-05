package edu.neu.coe.huskySort.sort.unicodeSort;

import edu.neu.coe.huskySort.sort.Sort;
import edu.neu.coe.huskySort.sort.compareSort.UnicodeHelper;
import edu.neu.coe.huskySort.sort.compareSort.UnicodeTimSort;
import edu.neu.coe.huskySort.sort.huskySort.PureHuskySort;
import edu.neu.coe.huskySort.sort.huskySortUtils.HuskyCoderFactory;
import edu.neu.coe.huskySort.sort.radix.UnicodeLSDSort;
import edu.neu.coe.huskySort.sort.radix.UnicodeMSDSort;
import edu.neu.coe.huskySort.sort.simple.QuickSort_DualPivot;
import edu.neu.coe.huskySort.sort.simple.TimSort;
import org.junit.Test;

import java.text.Collator;
import java.util.Arrays;
import java.util.Locale;

import static org.junit.Assert.assertArrayEquals;

/**
 * @author Caspar
 * @date 2021/12/5 02:54
 */
public class UnicodeSortTest {

    // MSD Sort for Chinese
    @Test
    public void testMSDSort() {
        final String[] input = {"陈富", "陈富贵","陈大炮","晨移入","陈健康","王大炮","陈达跑", "陈富", "陈富贵","陈大炮"
                ,"晨移入","陈健康","王大炮","陈达跑", "陈富", "陈富贵","陈大炮","晨移入","陈健康","王大炮","陈达跑", "陈富", "陈富贵","陈大炮","晨移入","陈健康","王大炮","陈达跑"};
        final String[] expected = {"晨移入", "晨移入", "晨移入", "晨移入", "陈达跑", "陈达跑","陈达跑", "陈达跑","陈大炮"
                ,"陈大炮","陈大炮","陈大炮","陈富","陈富","陈富","陈富","陈富贵","陈富贵","陈富贵","陈富贵","陈健康","陈健康","陈健康","陈健康", "王大炮", "王大炮", "王大炮", "王大炮"};

        UnicodeMSDSort unicodeMSDSort = new UnicodeMSDSort(Collator.getInstance(Locale.CHINA));
        unicodeMSDSort.sort(input);
        assertArrayEquals(expected, input);
    }

    // MSD Sort with custom keyLength for Chinese
    @Test
    public void testMSDSortWithKeyLengthCutoff() {
        final String[] input = {"陈富", "陈富贵","陈大炮","晨移入","陈健康","王大炮","陈达跑", "陈富", "陈富贵","陈大炮"
                ,"晨移入","陈健康","王大炮","陈达跑", "陈富", "陈富贵","陈大炮","晨移入","陈健康","王大炮","陈达跑", "陈富", "陈富贵","陈大炮","晨移入","陈健康","王大炮","陈达跑"};
        final String[] expected = {"晨移入", "晨移入", "晨移入", "晨移入", "陈达跑", "陈达跑","陈达跑", "陈达跑","陈大炮"
                ,"陈大炮","陈大炮","陈大炮","陈富","陈富","陈富","陈富","陈富贵","陈富贵","陈富贵","陈富贵","陈健康","陈健康","陈健康","陈健康", "王大炮", "王大炮", "王大炮", "王大炮"};

        UnicodeMSDSort unicodeMSDSort = new UnicodeMSDSort(Collator.getInstance(Locale.CHINA), 5);
        unicodeMSDSort.sort(input);
        assertArrayEquals(expected, input);
    }

    // LSD radix sort
    @Test
    public void testLSDRadixSort() {
        final String[] input = {"陈富", "陈富贵","陈大炮","晨移入","陈健康","王大炮","陈达跑", "陈富", "陈富贵","陈大炮"
                ,"晨移入","陈健康","王大炮","陈达跑", "陈富", "陈富贵","陈大炮","晨移入","陈健康","王大炮","陈达跑", "陈富", "陈富贵","陈大炮","晨移入","陈健康","王大炮","陈达跑"};
        final String[] expected = {"晨移入", "晨移入", "晨移入", "晨移入", "陈达跑", "陈达跑","陈达跑", "陈达跑","陈大炮"
                ,"陈大炮","陈大炮","陈大炮","陈富","陈富","陈富","陈富","陈富贵","陈富贵","陈富贵","陈富贵","陈健康","陈健康","陈健康","陈健康", "王大炮", "王大炮", "王大炮", "王大炮"};

        UnicodeLSDSort unicodeLSDSort = new UnicodeLSDSort(Collator.getInstance(Locale.CHINA));
        unicodeLSDSort.sort(input);
        assertArrayEquals(expected, input);
    }

    // LSD Sort with custom keyLength for Chinese
    @Test
    public void testLSDSortWithKeyLengthCutoff() {
        final String[] input = {"陈富", "陈富贵","陈大炮","晨移入","陈健康","王大炮","陈达跑", "陈富", "陈富贵","陈大炮"
                ,"晨移入","陈健康","王大炮","陈达跑", "陈富", "陈富贵","陈大炮","晨移入","陈健康","王大炮","陈达跑", "陈富", "陈富贵","陈大炮","晨移入","陈健康","王大炮","陈达跑"};
        final String[] expected = {"晨移入", "晨移入", "晨移入", "晨移入", "陈达跑", "陈达跑","陈达跑", "陈达跑","陈大炮"
                ,"陈大炮","陈大炮","陈大炮","陈富","陈富","陈富","陈富","陈富贵","陈富贵","陈富贵","陈富贵","陈健康","陈健康","陈健康","陈健康", "王大炮", "王大炮", "王大炮", "王大炮"};

        UnicodeLSDSort unicodeLSDSort = new UnicodeLSDSort(Collator.getInstance(Locale.CHINA), 5);
        unicodeLSDSort.sort(input);
        assertArrayEquals(expected, input);
    }

    // Quick Sort dual pivot sorting for Chinese
    @Test
    public void testQuicksortDualPivot() {
        final String[] input = {"陈富", "陈富贵","陈大炮","晨移入","陈健康","王大炮","陈达跑", "陈富", "陈富贵","陈大炮"
                ,"晨移入","陈健康","王大炮","陈达跑", "陈富", "陈富贵","陈大炮","晨移入","陈健康","王大炮","陈达跑", "陈富", "陈富贵","陈大炮","晨移入","陈健康","王大炮","陈达跑"};
        final String[] expected = {"晨移入", "晨移入", "晨移入", "晨移入", "陈达跑", "陈达跑","陈达跑", "陈达跑","陈大炮"
                ,"陈大炮","陈大炮","陈大炮","陈富","陈富","陈富","陈富","陈富贵","陈富贵","陈富贵","陈富贵","陈健康","陈健康","陈健康","陈健康", "王大炮", "王大炮", "王大炮", "王大炮"};

        Sort<String> s = new QuickSort_DualPivot<>(new UnicodeHelper("chinese string sorter", Collator.getInstance(Locale.CHINESE)));
        final String[] result = s.sort(input);
        assertArrayEquals(expected, result);
    }

    // Husky Sort for Chinese
    @Test
    public void testHuskySort() {
        // order:       453922  252568   145313   673679   181452   31014   988329   659494    923995   890721   744769   293165   520163   199395   669978   765753
        String[] xs = {"刘持平", "洪文胜", "樊辉辉", "苏会敏", "高民政", "曹玉德", "袁继鹏", "舒冬梅", "杨腊香", "许凤山", "王广风", "黄锡鸿", "罗庆富", "顾芳芳", "宋雪光", "王诗卉"};
        PureHuskySort<String> sorter = new PureHuskySort<>(HuskyCoderFactory.chineseEncoder, false, false);
        sorter.sort(xs);
        System.out.println(Arrays.toString(xs));
        // order:           31014   145313   181452   199395   252568   293165   453922  520163   659494    669978   673679  744769   765753   890721   923995    988329
        String[] sorted = {"曹玉德", "樊辉辉", "高民政", "顾芳芳", "洪文胜", "黄锡鸿", "刘持平", "罗庆富", "舒冬梅", "宋雪光", "苏会敏", "王广风", "王诗卉", "许凤山", "杨腊香", "袁继鹏"};
        assertArrayEquals(sorted, xs);
    }

    // Tim Sort for Chinese
    @Test
    public void testTimSort() {
        final String[] input = {"陈富", "陈富贵","陈大炮","晨移入","陈健康","王大炮","陈达跑", "陈富", "陈富贵","陈大炮"
                ,"晨移入","陈健康","王大炮","陈达跑", "陈富", "陈富贵","陈大炮","晨移入","陈健康","王大炮","陈达跑", "陈富", "陈富贵","陈大炮","晨移入","陈健康","王大炮","陈达跑"};
        final String[] expected = {"晨移入", "晨移入", "晨移入", "晨移入", "陈达跑", "陈达跑","陈达跑", "陈达跑","陈大炮"
                ,"陈大炮","陈大炮","陈大炮","陈富","陈富","陈富","陈富","陈富贵","陈富贵","陈富贵","陈富贵","陈健康","陈健康","陈健康","陈健康", "王大炮", "王大炮", "王大炮", "王大炮"};

        Sort<String> s = new UnicodeTimSort(new UnicodeHelper("chinese string sorter", Collator.getInstance(Locale.CHINESE)));
        s.sort(input, 0, input.length);
        assertArrayEquals(expected, input);
    }
}
