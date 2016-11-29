
package com.kaffatech.latte.commons.toolkit.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhen.ling
 *
 */
public class PagingUtils {

	public static final int MAX_PAGE_NO = 10;

	public static int getMaxPageNo(int recordTotal, int limit) {
		int maxPageNo = recordTotal / limit;
		if (recordTotal % limit > 0) {
			maxPageNo++;
		}
		
		return maxPageNo;
	}

	public static Integer getPrevPageNo(List<Integer> pageNoList, int pageNo) {
		if (CollectionUtils.isEmpty(pageNoList)) {
			return null;
		}
		if (pageNo > 1) {
			return pageNo - 1;
		}
		return 1;
	}

	public static Integer getNextPageNo(List<Integer> pageNoList, int pageNo,
			int limit, int recordTotal) {
		if (CollectionUtils.isEmpty(pageNoList)) {
			return null;
		}

		int maxPageNo = recordTotal / limit;
		if (recordTotal % limit > 0) {
			maxPageNo++;
		}

		if (pageNo < maxPageNo) {
			return pageNo + 1;
		}

		return maxPageNo;
	}

	public static List<Integer> getPageNoListByOffset(int offset, int limit,
			int recordTotal) {

		return getPageNoListByOffset(offset, limit, recordTotal, MAX_PAGE_NO);
	}

	public static List<Integer> getPageNoListByOffset(int offset, int limit,
			int recordTotal, int maxPageNo) {

		return getPageNoList(PagingUtils.getPageNo(offset, limit),
				PagingUtils.getPageNo(recordTotal - 1, limit), maxPageNo);
	}

	public static List<Integer> getPageNoList(int pageNo, int pageTotal) {

		return getPageNoList(pageNo, pageTotal, MAX_PAGE_NO);
	}

	public static List<Integer> getPageNoList(int pageNo, int pageTotal,
			int maxPageNo) {
		int pos = (maxPageNo / 2) + 1;

		int beginPageNo = Math.max(1, pageNo - pos + 1);

		if (pageTotal < beginPageNo + maxPageNo - 1) {
			// 尾页
			beginPageNo = Math.max(1, beginPageNo
					- (beginPageNo + maxPageNo - 1 - pageTotal));
		}

		Integer[] pageNoArray = new Integer[Math.min(pageTotal - beginPageNo
				+ 1, maxPageNo)];

		int eachPageNo = beginPageNo;
		for (int i = 0; i < pageNoArray.length; i++) {
			pageNoArray[i] = eachPageNo;
			eachPageNo++;
		}

		return Arrays.asList(pageNoArray);
	}

	public static int getOffset(int pageNo, int limit) {
		return (pageNo - 1) * limit;
	}

	public static int getPageNo(int offset, int limit) {
		return (offset / limit) + 1;
	}

	public static List getPageList(List list, int pageNo, int limit) {
		return getPageListByOffset(list, getOffset(pageNo, limit), limit);
	}

	public static List getPageListByOffset(List list, int offset, int limit) {
		if (list == null) {
			return null;
		}

		List pagedList = new ArrayList();
		for (int i = offset; i < offset + limit; i++) {
			if (list.size() > i) {
				pagedList.add(list.get(i));
			} else {
				break;
			}
		}
		return pagedList;
	}

	public static void main(String[] args) {
		List<Integer> pageNoArray = getPageNoList(9, 12, 10);
		for (int i = 0; i < pageNoArray.size(); i++) {
			System.out.print(pageNoArray.get(i));
			System.out.print(" ");
		}

		System.out.println(pageNoArray);

		System.out.println(getPageList(pageNoArray, 5, 2));

	}
}
