package com.ywp.nice.model;

import java.util.List;

/**
 * Created by Administrator on 2017/2/9.
 */

public class GankFuLiModel {
    /**
     * error : false
     * results : [{"_id":"589a7161421aa92db8a0041b","createdAt":"2017-02-08T09:16:17.678Z","desc":"2-8","publishedAt":"2017-02-08T11:39:51.371Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/2017-02-08-16230686_191036801373876_4789664128824246272_n.jpg","used":true,"who":"daimajia"},{"_id":"58993f2c421aa970b84523ab","createdAt":"2017-02-07T11:29:48.689Z","desc":"2-7","publishedAt":"2017-02-07T11:37:03.683Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/2017-02-07-032924.jpg","used":true,"who":"daimajia"},{"_id":"5897d9e9421aa970bb154891","createdAt":"2017-02-06T10:05:29.443Z","desc":"2-6","publishedAt":"2017-02-06T11:36:12.36Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/2017-02-06-16464762_1304611439576933_4803269855073533952_n.jpg","used":true,"who":"代码家"},{"_id":"58947b15421aa970bb154878","createdAt":"2017-02-03T20:44:05.311Z","desc":"2-4","publishedAt":"2017-02-04T11:47:42.336Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/2017-02-03-123209.jpg","used":true,"who":"代码家"},{"_id":"5893f544421aa90e69b17f8c","createdAt":"2017-02-03T11:13:08.967Z","desc":"2-3","publishedAt":"2017-02-03T11:52:22.806Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/16123958_1630476787257847_7576387494862651392_n.jpg","used":true,"who":"daimajia"},{"_id":"58857746421aa95eae2d92b3","createdAt":"2017-01-23T11:23:50.64Z","desc":"1-23","publishedAt":"2017-01-23T11:35:32.450Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/16124047_121657248344062_4191645221970247680_n.jpg","used":true,"who":"代码家"},{"_id":"58841c08421aa95ea9de7a0c","createdAt":"2017-01-22T10:42:16.648Z","desc":"1-22","publishedAt":"2017-01-22T11:39:29.779Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/16229501_482786908558452_6858241750058663936_n.jpg","used":true,"who":"daimajia"},{"_id":"58817e1e421aa9119735ac5f","createdAt":"2017-01-20T11:03:58.727Z","desc":"1-20","publishedAt":"2017-01-20T11:50:52.750Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/2017-01-20-030332.jpg","used":true,"who":"daimajia"},{"_id":"58802bea421aa9119a6ca6ab","createdAt":"2017-01-19T11:00:58.838Z","desc":"1-19","publishedAt":"2017-01-19T11:40:09.118Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/16124351_1863111260639981_4361246625721483264_n.jpg","used":true,"who":"daimajia"},{"_id":"587c6073421aa91194ca0e2c","createdAt":"2017-01-16T13:56:03.417Z","desc":"1-16","publishedAt":"2017-01-16T14:12:18.71Z","source":"chrome","type":"福利","url":"http://ww3.sinaimg.cn/large/610dc034gw1fbsfgssfrwj20u011h48y.jpg","used":true,"who":"daimajia"}]
     */

    private boolean error;
    private List<GankFuLiResultModel> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<GankFuLiResultModel> getResults() {
        return results;
    }

    public void setResults(List<GankFuLiResultModel> results) {
        this.results = results;
    }


}
