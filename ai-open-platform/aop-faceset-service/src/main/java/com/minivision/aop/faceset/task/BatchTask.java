package com.minivision.aop.faceset.task;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class BatchTask {
  private String taskId;
  private Date createTime = new Date();
  private int total;
  private AtomicInteger progress = new AtomicInteger();
  private int status;
  private String creator;
  protected BatchTaskContext taskContext;
  
  public static final int PREPARED = 0;
  public static final int RUNNING = 1;
  public static final int DONE = 2;
  
  public BatchTask(){}
  
  public BatchTask(String taskId, String creator, BatchTaskContext taskContext){
    this.taskId = taskId;
    this.creator = creator;
    this.taskContext = taskContext;
  }
  
  public String getTaskId() {
    return taskId;
  }
  public void setTaskId(String taskId) {
    this.taskId = taskId;
  }
  
  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }
  
  public int getCostTime(){
    return (int) ((System.currentTimeMillis()-createTime.getTime())/1000);
  }

  public int getTotal() {
    return total;
  }
  public void setTotal(int total) {
    this.total = total;
  }
  public int getProgress() {
    return progress.get();
  }
  
  public int getStatus() {
    return status;
  }
  public void setStatus(int status) {
    this.status = status;
  }
  
  public void progress(){
    if(this.progress.incrementAndGet() == this.total){
      this.status = DONE;
    }
    taskContext.sendStatus(taskId, this);
  }
  
  public String getCreator() {
    return creator;
  }
  public void setCreator(String creator) {
    this.creator = creator;
  }
  protected abstract void doTask();
  
  public void run(){
    this.status = RUNNING;
    doTask();
    //this.status = DONE;
    //taskContext.sendStatus(taskId, this);
  }
  
}
