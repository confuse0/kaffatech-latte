package com.kaffatech.latte.commons.io.util;

import com.kaffatech.latte.mainframe.dto.file.ImgDto;
import com.kaffatech.latte.commons.io.qiniu.util.QiniuFileUtils;

public class ImgUtils {

	private static String TINY = "imageView2/2/w/256/h/256";

	private static String SMALL = "imageView2/2/w/512/h/512";

	private static String MIDDLE = "imageView2/2/w/960/h/960";

	private static String LARGE = "imageView2/2/w/1280/h/1280";

	private static String EXTRA_LARGE = "imageView2/2/w/1280/h/1280";

	// private static String DEF_PATH = "dt/common/default";
	//
	// private static String DEF_EXTENSION = ".png";

	// @SuppressWarnings("unchecked")
	// public static ImgDto generateImgDto(String path, String code) {
	// List<String> defImgInfoList = (List<String>) ApplicationContextHolder
	// .getBean("defImgInfoList");
	//
	// Map<String, String> imgInfoMap = (Map<String, String>)
	// ApplicationContextHolder
	// .getBean("imgInfoMap");
	//
	// return generateImgDto(path, defImgInfoList, imgInfoMap.get(code));
	// }
	//
	// private static ImgDto generateImgDto(String path,
	// List<String> defaultImgInfoList, String imgInfo) {
	// // 设置图片本身信息
	// ImgDto img = BeanUtils.convert(generateBaseImg(path, imgInfo),
	// ImgDto.class);
	//
	// return img;
	// }

	// private static BaseImgDto generateDefImg(List<String> defaultImgInfoList)
	// {
	// BaseImgDto img = new BaseImgDto();
	// img.setPath(DEF_PATH + DEF_EXTENSION);
	// img.setUrl(generateUrl(img.getPath()));
	//
	// img.setTinyUrl(generateTinyUrl(img.getPath()));
	// img.setSmallUrl(generateSmallUrl(img.getPath()));
	// img.setMiddleUrl(generateMiddleUrl(img.getPath()));
	// img.setLargeUrl(generateLargeUrl(img.getPath()));
	// img.setExtraLargeUrl(generateExtraLargeUrl(img.getPath()));
	//
	// for (String param : defaultImgInfoList) {
	// String[] whArray = StringUtils.split(param, "/");
	//
	// String w = null;
	// boolean isW = false;
	// String h = null;
	// boolean isH = false;
	// for (String each : whArray) {
	// if (StringUtils.equals("w", each)) {
	// isW = true;
	// continue;
	// }
	// if (StringUtils.equals("h", each)) {
	// isH = true;
	// continue;
	// }
	// if (isW) {
	// w = each;
	// isW = false;
	// }
	// if (isH) {
	// h = each;
	// isH = false;
	// }
	// }
	//
	// StringBuilder keySb = new StringBuilder();
	//
	// if (!StringUtils.isEmpty(w)) {
	// keySb.append("w");
	// keySb.append(w);
	// }
	//
	// if (!StringUtils.isEmpty(h)) {
	// keySb.append("h");
	// keySb.append(h);
	// }
	//
	// String key = keySb.toString();
	//
	// if (!StringUtils.isEmpty(key)) {
	// img.getExt().put(
	// key,
	// QiniuFileUtils.generateUrl(DEF_PATH + "_" + w + "x" + h
	// + DEF_EXTENSION));
	// }
	// }
	//
	// return img;
	// }

	// private static BaseImgDto generateBaseImg(String path, String info) {
	// BaseImgDto img = new BaseImgDto();
	// img.setPath(path);
	// img.setUrl(generateUrl(path));
	//
	// img.setTinyUrl(generateTinyUrl(img.getPath()));
	// img.setSmallUrl(generateSmallUrl(img.getPath()));
	// img.setMiddleUrl(generateMiddleUrl(img.getPath()));
	// img.setLargeUrl(generateLargeUrl(img.getPath()));
	// img.setExtraLargeUrl(generateExtraLargeUrl(img.getPath()));
	//
	// if (!StringUtils.isEmpty(info)) {
	// String[] array = StringUtils.split(info, ";");
	//
	// for (String param : array) {
	//
	// String[] kv = StringUtils.split(param, ":");
	//
	// String keyprefix = "";
	// String value = "";
	// if (kv.length > 1) {
	// // 设置key前缀
	// keyprefix = kv[0];
	// value = kv[1];
	// } else {
	// value = kv[0];
	// }
	//
	// String[] whArray = StringUtils.split(value, "/");
	//
	// String w = null;
	// boolean isW = false;
	// String h = null;
	// boolean isH = false;
	// for (String each : whArray) {
	// if (StringUtils.equals("w", each)) {
	// isW = true;
	// continue;
	// }
	// if (StringUtils.equals("h", each)) {
	// isH = true;
	// continue;
	// }
	// if (isW) {
	// w = each;
	// isW = false;
	// }
	// if (isH) {
	// h = each;
	// isH = false;
	// }
	// }
	//
	// StringBuilder keySb = new StringBuilder();
	// keySb.append(keyprefix);
	//
	// if (!StringUtils.isEmpty(w)) {
	// keySb.append("w");
	// keySb.append(w);
	// }
	//
	// if (!StringUtils.isEmpty(h)) {
	// keySb.append("h");
	// keySb.append(h);
	// }
	//
	// String key = keySb.toString();
	//
	// if (!StringUtils.isEmpty(key)) {
	// img.getExt().put(key,
	// QiniuFileUtils.generateUrl(path, value));
	// }
	// }
	// }
	//
	// return img;
	//
	// }

	public static ImgDto generateImgDto(String path) {
		ImgDto img = new ImgDto();
		img.setPath(path);
		img.setUrl(generateUrl(path));

		return img;
	}

	public static String generateUrl(String path) {
		return QiniuFileUtils.generateUrl(path);
	}

	public static String generateTinyUrl(String path) {
		return QiniuFileUtils.generateUrl(path, TINY);
	}

	public static String generateSmallUrl(String path) {
		return QiniuFileUtils.generateUrl(path, SMALL);

	}

	public static String generateMiddleUrl(String path) {
		return QiniuFileUtils.generateUrl(path, MIDDLE);

	}

	public static String generateLargeUrl(String path) {
		return QiniuFileUtils.generateUrl(path, LARGE);
	}

	public static String generateExtraLargeUrl(String path) {
		return QiniuFileUtils.generateUrl(path, EXTRA_LARGE);
	}

	public static int[] calculateSize(int width, int height, int maxWidth,
			int maxHeight) {
		double wBe = width / maxWidth;
		double hBe = height / maxHeight;
		double be = wBe > hBe ? wBe : hBe;

		if (be <= 1) {
			be = 1;
		}

		double w = width / be;
		double h = height / be;

		int[] ret = new int[] { (int) w, (int) h };

		return ret;
	}
}
