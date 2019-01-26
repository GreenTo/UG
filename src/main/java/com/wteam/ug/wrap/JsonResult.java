package com.wteam.ug.wrap;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 统一返回给前端的Json数据
 */
@ApiModel(value = "jsonResult",description = "通用返回对象")
public class JsonResult<T> implements Serializable {
  /**
   * 状态码
   */
  @ApiModelProperty(value = "调用是否正常,非200即错误",name = "state")
  private Integer state;
  /**
   * 状态信息
   */
  @ApiModelProperty(value = "接口返回msg,如果state不是200,即为错误信息",name = "msg")
  private String msg;
  /**
   * 数据统计
   */
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private Integer count;
  /**
   * 数据
   */
  @ApiModelProperty(value = "调用成功,返回的数据",name = "data")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private  T data;

  public JsonResult(T data) {
    this.state= CodeStateEnum.SUCCESS.getState();
    this.msg=CodeStateEnum.SUCCESS.getStateInfo();
    this.data = data;
  }

  public JsonResult(T data,int count) {
    this.state=CodeStateEnum.SUCCESS.getState();
    this.msg=CodeStateEnum.SUCCESS.getStateInfo();
    this.data = data;
    this.count=count;
  }

  public JsonResult(CodeStateEnum codeStateEnum, T data){
    this.state=codeStateEnum.getState();
    this.msg=codeStateEnum.getStateInfo();
    this.data = data;
  }

  public JsonResult(CodeStateEnum codeStateEnum, T data,int count){
    this.state=codeStateEnum.getState();
    this.msg=codeStateEnum.getStateInfo();
    this.data = data;
    this.count=count;
  }

  public JsonResult(CodeStateEnum codeStateEnum){
    this.state=codeStateEnum.getState();
    this.msg=codeStateEnum.getStateInfo();
  }

  public JsonResult(){
  }

  public JsonResult(Integer state, String msg) {
    this.state=state;
    this.msg=msg;
  }

  public JsonResult erro(){
    this.state=CodeStateEnum.ERRO.getState();
    this.msg=CodeStateEnum.ERRO.getStateInfo();
    return this;
  }
  public JsonResult erro(String msg){
    this.state=CodeStateEnum.ERRO.getState();
    this.msg=msg;
    return this;
  }

  public JsonResult ok(){
    this.state=CodeStateEnum.SUCCESS.getState();
    this.msg=CodeStateEnum.SUCCESS.getStateInfo();
    return this;
  }

  public JsonResult ok(T data){
    this.state=CodeStateEnum.SUCCESS.getState();
    this.msg=CodeStateEnum.SUCCESS.getStateInfo();
    this.data = data;
    return this;
  }

  public JsonResult ok(T data,int count){
    this.state=CodeStateEnum.SUCCESS.getState();
    this.msg=CodeStateEnum.SUCCESS.getStateInfo();
    this.data = data;
    this.count=count;
    return this;
  }

  public Integer getState() {
    return state;
  }

  public void setState(Integer state) {
    this.state = state;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public Integer getCount() {
    return count;
  }

  public void setCount(Integer count) {
    this.count = count;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }
}
