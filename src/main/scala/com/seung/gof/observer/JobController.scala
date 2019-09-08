package com.seung.gof.observer

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import scala.collection.mutable.ArrayBuffer


object JobControllerMain extends App {
  BackupJobDefinition.addObserver(LogBackupJobObserver())
  BackupJobDefinition.addObserver(MailBackupJobObserver("test@example.com"))
  BackupJobDefinition.execute()
}

case class BackupJob(private val fileName: String) extends AbstractJob {
  def getFileName(): String = {
    fileName
  }

  override def run(): Unit = {
    println(s"pg_dumpall >$fileName\n")
  }
}

case class LogBackupJobObserver() extends JobObserver[BackupJob] {
  override def update(job: BackupJob): Unit = {
    println(s"[INFO] Backup: ${job.getFileName()}  Status: ${job.getStatus()}")
  }
}

case class MailBackupJobObserver(private val mailAddress: String) extends JobObserver[BackupJob] {
  override def update(job: BackupJob): Unit = {
    if (job.isFinished()) {
      println(s"echo 'Finished to backup to the ${job.getFileName()}' | mail -s 'Backup notification' $mailAddress")
    }
  }
}

object BackupJobDefinition extends JobDefinition[BackupJob] {
  override protected var observers = new ArrayBuffer[JobObserver[BackupJob]]()
  private val FLE_NAME_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("'/backup/'yyyy_MM_dd'.sql'")

  override protected def makeJob(): BackupJob = {
    val startDate = LocalDate.now()
    val fileName = startDate.format(FLE_NAME_FORMATTER)
    BackupJob(fileName)
  }
}

trait JobObservable[T <: AbstractJob] {
  def execute(): Unit

  protected def makeJob(): T
}

abstract class JobDefinition[T <: AbstractJob] extends JobObservable[T] {
  protected var observers: ArrayBuffer[JobObserver[T]]

  def addObserver[A <: JobObserver[T]](observer: A): Unit = {
    observers += observer
  }

  def execute(): Unit = {
    val job = makeJob()
    notifyObservers(job)
    job.execute()
    notifyObservers(job)
  }

  def notifyObservers(job: T): Unit = {
    observers.foreach(o => o.update(job))
  }

}

abstract class AbstractJob {

  sealed abstract class STATUS

  case class UNFINISHED() extends STATUS

  case class FINISHED() extends STATUS

  var status: STATUS = UNFINISHED()

  def getStatus(): STATUS = {
    this.status
  }

  def isFinished(): Boolean = {
    this.status match {
      case FINISHED() => true
      case UNFINISHED() => false
    }
  }

  def execute(): Unit = {
    run()
    this.status = FINISHED()
  }

  def run(): Unit

}

trait JobObserver[A <: AbstractJob] {
  def update(job: A): Unit

}

