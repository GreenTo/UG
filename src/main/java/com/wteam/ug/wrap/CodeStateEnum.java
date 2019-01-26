package com.wteam.ug.wrap;

public enum  CodeStateEnum {
  /**
   * 状态码基本信息
   */
  SUCCESS(200,"请求成功"),
  NULL(204,"空资源"),
  OLD(301,"资源的URI已被更新"),
  NotModified(304,"资源未更改（缓存）"),
  BAD(400,"传入参数错误"),
  OVER(400,"参数超出范围"),
  NotFound(404,"资源不存在"),
  NotAccept(406,"服务端不支持"),
  ERRO(500,"请求失败"),
  NotGo(501,"验证器拦截"),
  REJECT(502,"拦截到用户token出错"),
  Unavailable(503,"服务端无法处理请求"),
  CHECK(0,"审核中"),OFFLINE(-1,"非法"),
  PASS(1,"通过认证"),INNER_ERROR(-1001,"内部系统错误")
  ;

  private int state;
  private String stateInfo;

  private CodeStateEnum(int state, String stateInfo) {
    this.state = state;
    this.stateInfo = stateInfo;
  }
  public static CodeStateEnum stateOf(int state){
    for (CodeStateEnum stateEnum:values()){
      if (stateEnum.getState()==state){
        return stateEnum;
      }
    }
    return null;
  }

  public int getState() {
    return state;
  }

  public String getStateInfo() {
    return stateInfo;
  }
}
