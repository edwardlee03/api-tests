package leet.code.sliding.window;

import java.util.HashMap;
import java.util.Map;

/**
 * 76. 最小覆盖子串
 * <p></p>
 * https://leetcode-cn.com/problems/minimum-window-substring/
 * <pre>
 * 给你一个字符串 s 、一个字符串 t 。
 * 返回 s 中涵盖 t 所有字符的最小子串。
 * 如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 "" 。
 *
 * 注意：
 * * 对于 t 中重复字符，我们寻找的子字符串中该字符数量必须不少于 t 中该字符数量。
 * * 如果 s 中存在这样的子串，我们保证它是唯一的答案。
 *
 * 示例 1：
 * 输入：s = "ADOBECODEBANC", t = "ABC"
 * 输出："BANC"
 *
 * 示例 2：
 * 输入：s = "a", t = "a"
 * 输出："a"
 *
 * 示例 3：
 * 输入: s = "a", t = "aa"
 * 输出: ""
 * 解释: t 中两个字符 'a' 均应包含在 s 的子串中，
 * 因此没有符合条件的子字符串，返回空字符串。
 *
 * 提示：
 * 1 <= s.length, t.length <= 105
 * s 和 t 由英文字母组成
 *
 * 进阶：你能设计一个在 O(n) 时间内解决此问题的算法吗？
 * </pre>
 * <pre>
 * 1.认识问题
 * 一个字符串 s 、一个字符串 t
 * s 中涵盖 t 所有字符的最小子串
 * 对于 t 中重复字符，我们寻找的子字符串中该字符数量必须不少于 t 中该字符数量。
 * 如果 s 中存在这样的子串，我们保证它是唯一的答案。
 * s 和 t 由英文字母组成
 *
 * 全局最优解
 * 重复字符计数器，哈希表
 * 在 O(n) 时间内解决此问题的算法，滑动窗口
 * </pre>
 *
 * @author guangyi
 */
public class MinWindow {

    public static String minWindow(String str, String target) {
        if (target.length() > str.length()) {
            return "";
        }
        // 记录最短子串的开始位置和长度
        int start = 0;
        int minLength = Integer.MAX_VALUE;

        // 滑动窗口计数器
        Map<Character, Integer> window = new HashMap<>(16);
        // 目标字符串
        Map<Character, Integer> needs = new HashMap<>(16);
        int targetLength = target.length();
        for (int i = 0; i < targetLength; i++) {
            char ch = target.charAt(i);
            needs.put(ch, needs.getOrDefault(ch, 0) + 1);
        }
        int needsSize = needs.size();
        // 记录window中已经有多少个字符符合要求了
        int match = 0;

        int left = 0;
        int right = 0;
        int strLength = str.length();
        while (right < strLength) {
            char rightChar = str.charAt(right);
            Integer needCount = needs.get(rightChar);
            if (needCount != null) {
                // 加入window
                int windowCount = window.getOrDefault(rightChar, 0) + 1;
                window.put(rightChar, windowCount);
                // 巧妙：等于判断规避了重复字符
                if (windowCount == needCount) {
                    // 这个字符的出现次数符合要求了
                    match += 1;
                }
            }
            right++;

            while (match == needsSize) {
                // window中的字符串已符合needs的要求了
                // 找到一个可行解
                int subLength = right - left;
                if (subLength < minLength) {
                    // 找到一个更优解，更新最小子串的开始位置和长度
                    start = left;
                    minLength = subLength;
                }
                char leftChar = str.charAt(left);
                needCount = needs.get(leftChar);
                if (needCount != null) {
                    // 移出window
                    int windowCount = window.get(leftChar) - 1;
                    window.put(leftChar, windowCount);
                    // 巧妙：小于判断规避了重复字符
                    if (windowCount < needCount) {
                        // 这个字符出现次数不再符合要求
                        match -= 1;
                    }
                }
                left++;
            }
        }
        return minLength == Integer.MAX_VALUE ? "" :
                str.substring(start, start + minLength);
    }
}
