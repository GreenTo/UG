package com.wteam.ug.wrap;

public class ResultFactory<T> {

  public static JsonResult ok(){
    return new JsonResult().ok();
  }

  public static JsonResult erro(){
    return new JsonResult().erro();
  }

  public static JsonResult erro(String msg){
    return new JsonResult().erro();
  }

  public static JsonResult ok(Object data){
    return new JsonResult().ok(data);
  }

  //count:返回统计数量
  public static JsonResult ok(Object data,int count){
    return new JsonResult().ok(data,count);
  }
  //失败
  public static JsonResult erro(CodeStateEnum codeStateEnum){
    return new JsonResult(codeStateEnum);
  }

  public static JsonResult custom(Integer state,String msg){
    return new JsonResult(state,msg);
  }


  public static JsonResult empty(){
    return new JsonResult(CodeStateEnum.NULL);
  }

  public static JsonResult bad(){
    return new JsonResult(CodeStateEnum.BAD);
  }

  public static JsonResult over(){
    return new JsonResult(CodeStateEnum.OVER);
  }

  public static JsonResult unavailable(){
    return new JsonResult(CodeStateEnum.Unavailable);
  }

  public static JsonResult notGo(){
    return new JsonResult(CodeStateEnum.NotGo);
  }
}
