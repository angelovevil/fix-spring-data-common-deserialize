/**
 * 由于spring的分页参数类对参数进行了限制,null,0,空字符串都是不可用的,所以在hessian反序列化时,先创建类,会出现错误.
 * 此包下的内容仅为了解决hessian反序列化问题.
 * 处理了pageable,page,sort,order的问题
 * Created by 刘一波 on 15/4/6.
 * E-Mail:obiteaaron@gmail.com
 */
package com.github.obiteaaron.common.data;