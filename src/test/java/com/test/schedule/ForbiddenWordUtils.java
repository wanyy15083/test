package com.test.schedule;

import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by songyigui on 2017/6/21.
 */
public class ForbiddenWordUtils {
    private static final Logger LOGGER = LogManager.getLogger(ForbiddenWordUtils.class);

    /**
     * 默认的遮罩文字
     */
    private static final String DEFAULT_MASK = "***";
    /**
     * 屏蔽关键词抓取的url
     */
    private static String forbiddenWordFetchURL;

    /**
     * 屏蔽关键词抓取时间间隔 毫秒
     */
    private static int reloadInterval = 60000; //10分钟

    /**
     * 屏蔽关键词
     */
    private static List<Pattern> forbiddenWords;

    public static void setForbiddenWordFetchURL(String forbiddenWordFetchURL) {
        ForbiddenWordUtils.forbiddenWordFetchURL = forbiddenWordFetchURL;
    }

    public static void setReloadInterval(int reloadInterval) {
        ForbiddenWordUtils.reloadInterval = reloadInterval;
    }

    /**
     * 替换input中的屏蔽关键词为默认的掩码
     *
     * @param input
     * @return
     */
    public static String replace(String input) {
        return replace(input, DEFAULT_MASK);
    }

    /**
     * 将屏蔽关键词替换为mask
     *
     * @param input
     * @param mask
     * @return
     */
    public static String replace(String input, String mask) {
        for (int i = 0; i < forbiddenWords.size(); i++) {
            Pattern pattern = forbiddenWords.get(i);
            input = pattern.matcher(input).replaceAll(mask);
        }
        return input;
    }

    /**
     * 是否包含屏蔽关键词
     *
     * @param input
     * @return
     */
    public static boolean containsForbiddenWord(String input) {
        for (int i = 0; i < forbiddenWords.size(); i++) {
            Pattern pattern = forbiddenWords.get(i);
            if (pattern.matcher(input).find())
                return true;
        }
        return false;
    }

    static {
        InputStream is = null;
        try {
            String filename = "forbidden.txt";
            is = ForbiddenWordUtils.class.getClassLoader().getResourceAsStream(filename);
            byte[] fileCBytes = IOUtils.toByteArray(is);
            ForbiddenWordUtils.loadForbiddenWords(fileCBytes);
        } catch (IOException e) {
            LOGGER.error("read forbidden file failed", e);
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    public static void initRemoteFetch() {
        RemoteFileFetcher.createPeriodFetcher(
                forbiddenWordFetchURL,
                reloadInterval,
                new RemoteFileFetcher.FileChangeListener() {
                    @Override
                    public void fileReloaded(byte[] fileContent) throws IOException {
                        ForbiddenWordUtils.loadForbiddenWords(fileContent);
                    }
                });
    }

    private static void loadForbiddenWords(byte[] fileCBytes) {
        Reader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(fileCBytes), "UTF-8"));
            List<String> forbiddenWordsStrList = IOUtils.readLines(reader);
            forbiddenWords = Lists.newArrayList();
            for (int i = forbiddenWordsStrList.size() - 1; i >= 0; i--) {
                String foribiddenWord = forbiddenWordsStrList.get(i).trim();
                if (foribiddenWord.length() == 0 || foribiddenWord.startsWith("#")) {
                    continue;
                } else {
                    forbiddenWords.add(Pattern.compile(foribiddenWord));
                }
            }
        } catch (Exception e) {
            LOGGER.error("load forbidden words failed", e);
        }

    }
}
