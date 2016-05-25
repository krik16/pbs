package com.shouyingbao.pbs.core.bean;

import java.io.Serializable;

import net.sf.json.JSONObject;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/*
使用示例说明：
{
    "meta": {
        "msg": "success",                     //返回的是错误提示信息
        "errno": 0                            //0代表成功  1，代表失败
    },
    "result": {
        "data":{} or []                       //多行记录返回数组
        "page":{                              //page 是可选项，data为数组时才会有page，也可以无page信息（不分页）。
            "currentPage"":1 ,                //当前页,目前系统有从0或1开始。统一1开始
            "pageSize":10,                    //分页的数量
            "totalPage":1,                    //总页数
            "totalCount":10                   //总行数
        }
    }
}
*/

/**
 * 系统返回值对象
 *
 * @author Breggor
 */
public class ResponseVO implements java.io.Serializable {

    public static final Meta SUCCESS = new Meta(0, "success"); //成功
    public static final Meta FAILURE = new Meta(1, "failure"); //失败

    private Meta meta;      //errno=0：成功, errno=1：失败
    private Result result;  //数据
    
    public ResponseVO(){
    	
    }


    /**
     * 成功返回值对象
     *
     * @return ResponseData
     */
    public static ResponseVO success() {
        return new ResponseVO(ResponseVO.SUCCESS, null);
    }

    /**
     * 成功返回值对象
     *
     * @param data 数据
     * @return ResponseData
     */
    public static <T> ResponseVO success(T data) {
        return new ResponseVO(ResponseVO.SUCCESS, new Result(data, null));
    }

    /**
     * 分页成功返回值对象
     *
     * @param data        数据
     * @param currentPage 当前页
     * @param pageSize    每页大小
     * @param totalCount  总行数
     * @return ResponseData
     */
    public static <T> ResponseVO success(T data, Integer currentPage, Integer pageSize, Integer totalCount) {
        return new ResponseVO(ResponseVO.SUCCESS, data, currentPage, pageSize, totalCount);
    }


    /**
     * 失败返回值对象
     *
     * @return ResponseData
     */
    public static ResponseVO failure() {
        return new ResponseVO(ResponseVO.FAILURE, null);
    }


    /**
     * 失败码/失败信息回值对象
     *
     * @param errno
     * @param msg
     * @return ResponseData
     */
    public static ResponseVO failure(int errno, String msg) {
        return new ResponseVO(new Meta(errno, msg), null);
    }
    
    /**
     * 失败码/失败信息回值对象
     *
     * @param errno
     * @param msg
     * @return ResponseData
     */
    public static <T> ResponseVO failure(int errno, String msg, T data) {
        return new ResponseVO(new Meta(errno, msg), new Result(data, null));
    }


    private <T> ResponseVO(Meta meta, T data, Integer currentPage, Integer pageSize, Integer totalCount) {
        this.meta = meta;
        if (data != null && currentPage != null && pageSize != null && totalCount != null) {
            this.result = new Result(data, new Page(currentPage, pageSize, totalCount)); ;
        }
    }


    private ResponseVO(Meta meta, Result result) {
        this.meta = meta;
        this.result = result;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(int errno, String msg) {
        this.meta = new Meta(errno, msg);
    }

    public void setMeta(Meta meta) {this.meta=meta;}

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Result<T> implements Serializable {
        private Page page;
        private T data;

        private Result() {}

        private Result(T data, Page page) {
            this.data = data;
            this.page = page;
        }

        public Page getPage() {
            return page;
        }

        public void setPage(Page page) {
            this.page = page;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                    .append("page", page)
                    .append("data", data)
                    .toString();
        }
    }

    public static class Meta implements Serializable {
        private int errno; //错误码
        private String msg;  //提示信息

        private Meta() {}

        private Meta(int errno, String msg) {
            this.errno = errno;
            this.msg = msg;
        }

        public void setErrno(int errno) {
            this.errno = errno;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }


        public int getErrno() {
            return errno;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (o == null || getClass() != o.getClass()) return false;

            Meta meta = (Meta) o;

            return new EqualsBuilder()
                    .append(errno, meta.errno)
                    .append(msg, meta.msg)
                    .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37)
                    .append(errno)
                    .append(msg)
                    .toHashCode();
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                    .append("errno", errno)
                    .append("msg", msg)
                    .toString();
        }
    }

    public static class Page implements Serializable {
        private Integer currentPage;//当前页：统一从1开始
        private Integer pageSize = 10; //每页行数
        private Integer totalCount; //总行数
        private Integer totalPage = 0;//总页数

        private Page() {}

        private Page(Integer currentPage, Integer pageSize, Integer totalCount) {
            this.currentPage = currentPage;
            this.pageSize = pageSize;
            this.totalCount = totalCount;
        }

        public void setCurrentPage(Integer currentPage) {
            this.currentPage = currentPage;
        }

        public void setPageSize(Integer pageSize) {
            this.pageSize = pageSize;
        }

        public void setTotalCount(Integer totalCount) {
            this.totalCount = totalCount;
        }

        public void setTotalPage(Integer totalPage) {
            this.totalPage = totalPage;
        }

        public Integer getCurrentPage() {
            return currentPage;
        }


        public Integer getPageSize() {
            return pageSize;
        }


        public Integer getTotalCount() {
            return totalCount;
        }

        /**
         * 总页数
         *
         * @return
         */
        public Integer getTotalPage() {
            if (this.pageSize != null && this.pageSize > 0) {
                if (totalCount % pageSize == 0) {
                    totalPage = totalCount / pageSize;
                } else {
                    totalPage = totalCount / pageSize + 1;
                }
            }
            return totalPage;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                    .append("currentPage", currentPage)
                    .append("pageSize", pageSize)
                    .append("totalCount", totalCount)
                    .toString();
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("meta", meta)
                .append("result", result)
                .toString();
    }

    public static void main(String[] args) {
        System.out.println(JSONObject.fromObject(ResponseVO.success()));
        System.out.println(JSONObject.fromObject(ResponseVO.success("处理成功：返回单个对象")));
        System.out.println(JSONObject.fromObject(ResponseVO.success("处理成功：返回对象集合List<Object>", 1, 10, 200)));
        System.out.println(JSONObject.fromObject(ResponseVO.failure()));
        System.out.println(JSONObject.fromObject(ResponseVO.failure(1020001, "业务出错")));
    }
}
