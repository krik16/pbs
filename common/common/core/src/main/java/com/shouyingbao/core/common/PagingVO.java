package com.shouyingbao.core.common;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页DTO
 * 保存分页相关信息
 *
 * @param <T> 实体对象
 * @author 堵成杰
 * @since 2.0
 */
public class PagingVO<T> implements Serializable {

    /**
     * SID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 分页数据列表
     */
    private List<T> dataList;

    /**
     * 分页号码列表
     */
    private List<String> noList;

    /**
     * 记录总数
     * 废弃
     */
    @Deprecated
    private int rowCnt;

    /**
     * 记录总数
     */
    private int totalSize;

    /**
     * 页总数
     */
    private int totalPage;

    /**
     * 当前页
     */
    private int currentPage;

    /**
     * 单页记录数
     */
    private int pageSize;

    /**
     * 当前页开始记录
     */
    private int offset;

    /**
     * 默认单页记录数
     */
    public static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * 默认当前页
     */
    public static final int DEFAULT_CURRENT_PAGE = 1;

    public static final int DEFAULT_CRITICAL_1 = 5;
    public static final int DEFAULT_CRITICAL_2 = 9;
    public static final int DEFAULT_CRITICAL_3 = 13;
    public static final String DEFAULT_ELIPSIS = "...";

    /**
     * 构造函数
     *
     * @param pageSize    单页记录数
     * @param currentPage 当前页
     * @param totalSize   总记录数
     */
    public PagingVO(int pageSize, int currentPage, int totalSize) {

        if (pageSize <= 0) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        if (currentPage <= 0) {
            currentPage = DEFAULT_CURRENT_PAGE;
        }

        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.totalSize = totalSize;
        this.totalPage = (totalSize - 1) / pageSize + 1;

        this.rowCnt = totalSize;
        this.totalPage = (rowCnt - 1) / pageSize + 1;

        /*if(currentPage > totalPage){
            currentPage = totalPage;
		}*/
        this.offset = pageSize * (currentPage - 1);
        buildPageNoList();
    }

    /**
     * solr分页时的构造函数
     *
     * @param _pageSize    单页记录数
     * @param _currentPage 当前页
     */
    public PagingVO(int _pageSize, Integer _currentPage) {

        if (_pageSize <= 0) {
            _pageSize = DEFAULT_PAGE_SIZE;
        }
        if (_currentPage == null) {
            _currentPage = DEFAULT_CURRENT_PAGE;
        }
        this.pageSize = _pageSize;
        this.currentPage = _currentPage;
        /*if(currentPage > totalPage){
            currentPage = totalPage;
		}*/
        this.offset = pageSize * (currentPage - 1);
        buildPageNoList();
    }

    /**
     * 创建分页号列表
     * <p/>
     * 总页数<=5	[1,2,3,4,5]
     * 总页数>5 && 总页数<=9
     * 当前页>=1 && 当前页<5	 [1,2,3,4,5...]
     * 当前页>=5 && 当前页<=9 [1,2,3,4,5,6,7,8,9]
     * 总页数 > 9 && 总页数 <= 13
     * 当前页>=1 && 当前页<5	 [1,2,3,4,5...]
     * 当前页>=5 && 当前页<9  [1,2,3,4,5,6,7,8,9...]
     * 当前页>=9 && 当前页<=13[1,2...9,10,11,12,13]
     * 总页数 > 13
     * 当前页>=1 && 当前页<5	 [1,2,3,4,5...]
     * 当前页>=5 && 当前页<9  [1,2,3,4,5,6,7,8,9...]
     * 当前页>=9
     * 当前页 >  总页数 -5 [1,2...总页数-4,总页数-3,总页数-2,总页数-1,总页数]
     * 当前页 <= 总页数 -5 [1,2...当前页,当前页+1,当前页+2,当前页+3,当前页+4...]
     */
    public void buildPageNoList() {

        noList = new ArrayList<String>();

        if (totalPage <= DEFAULT_CRITICAL_1) {

            for (int i = 1; i <= totalPage; i++) {

                noList.add(String.valueOf(i));
            }

        } else if (totalPage > DEFAULT_CRITICAL_1 && totalPage <= DEFAULT_CRITICAL_2) {

            if (currentPage < DEFAULT_CRITICAL_1) {

                for (int i = 1; i <= DEFAULT_CRITICAL_1; i++) {

                    noList.add(String.valueOf(i));
                }
                noList.add(DEFAULT_ELIPSIS);
            } else {

                for (int i = 1; i <= totalPage; i++) {

                    noList.add(String.valueOf(i));
                }
            }
        } else if (totalPage > DEFAULT_CRITICAL_2 && totalPage <= DEFAULT_CRITICAL_3) {

            if (currentPage < DEFAULT_CRITICAL_1) {

                for (int i = 1; i <= DEFAULT_CRITICAL_1; i++) {

                    noList.add(String.valueOf(i));
                }
                noList.add(DEFAULT_ELIPSIS);
            } else if (currentPage >= DEFAULT_CRITICAL_1 && currentPage < DEFAULT_CRITICAL_2) {

                for (int i = 1; i <= DEFAULT_CRITICAL_2; i++) {

                    noList.add(String.valueOf(i));
                }
                noList.add(DEFAULT_ELIPSIS);
            } else {

                noList.add(String.valueOf(1));
                noList.add(String.valueOf(2));
                noList.add(DEFAULT_ELIPSIS);
                for (int i = DEFAULT_CRITICAL_2; i <= totalPage; i++) {

                    noList.add(String.valueOf(i));
                }
            }
        } else {

            if (currentPage < DEFAULT_CRITICAL_1) {

                for (int i = 1; i <= DEFAULT_CRITICAL_1; i++) {

                    noList.add(String.valueOf(i));
                }
                noList.add(DEFAULT_ELIPSIS);
            } else if (currentPage >= DEFAULT_CRITICAL_1 && currentPage < DEFAULT_CRITICAL_2) {

                for (int i = 1; i <= DEFAULT_CRITICAL_2; i++) {

                    noList.add(String.valueOf(i));
                }
                noList.add(DEFAULT_ELIPSIS);
            } else if (currentPage > totalPage - DEFAULT_CRITICAL_1) {
                noList.add(String.valueOf(1));
                noList.add(String.valueOf(2));
                noList.add(DEFAULT_ELIPSIS);
                for (int i = totalPage - DEFAULT_CRITICAL_1 + 1; i <= totalPage; i++) {

                    noList.add(String.valueOf(i));
                }
            } else if (currentPage <= totalPage - DEFAULT_CRITICAL_1) {
                noList.add(String.valueOf(1));
                noList.add(String.valueOf(2));
                noList.add(DEFAULT_ELIPSIS);
                for (int i = currentPage; i < currentPage + DEFAULT_CRITICAL_1; i++) {

                    noList.add(String.valueOf(i));
                }
                noList.add(DEFAULT_ELIPSIS);
            }
        }

    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public List<String> getNoList() {
        return noList;
    }

    public void setNoList(List<String> noList) {
        this.noList = noList;
    }

    public int getTotalPage() {
        return totalPage;
    }


    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }


    public int getCurrentPage() {
        return currentPage;
    }


    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }


    public int getPageSize() {
        return pageSize;
    }


    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Deprecated
    public int getRowCnt() {
        return rowCnt;
    }

    @Deprecated
    public void setRowCnt(int rowCnt) {
        this.setTotalSize(rowCnt);
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
        this.totalPage = (totalSize - 1) / pageSize + 1;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("dataList", dataList)
                .append("noList", noList)
                .append("rowCnt", rowCnt)
                .append("totalSize", totalSize)
                .append("totalPage", totalPage)
                .append("currentPage", currentPage)
                .append("pageSize", pageSize)
                .append("offset", offset)
                .toString();
    }
}
