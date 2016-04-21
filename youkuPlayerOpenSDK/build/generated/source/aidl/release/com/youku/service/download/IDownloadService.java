/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: E:\\GitHub\\Dota2Helper\\youkuPlayerOpenSDK\\src\\main\\aidl\\com\\youku\\service\\download\\IDownloadService.aidl
 */
package com.youku.service.download;
/**
 * IDownloadService.下载服务接口
 * 
 * @author 刘仲男 qq81595157@126.com
 * @version v3.5
 * @created time 2013-10-5 下午1:16:02
 */
public interface IDownloadService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.youku.service.download.IDownloadService
{
private static final java.lang.String DESCRIPTOR = "com.youku.service.download.IDownloadService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.youku.service.download.IDownloadService interface,
 * generating a proxy if needed.
 */
public static com.youku.service.download.IDownloadService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.youku.service.download.IDownloadService))) {
return ((com.youku.service.download.IDownloadService)iin);
}
return new com.youku.service.download.IDownloadService.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_registerCallback:
{
data.enforceInterface(DESCRIPTOR);
com.youku.service.download.ICallback _arg0;
_arg0 = com.youku.service.download.ICallback.Stub.asInterface(data.readStrongBinder());
this.registerCallback(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_unregister:
{
data.enforceInterface(DESCRIPTOR);
this.unregister();
reply.writeNoException();
return true;
}
case TRANSACTION_createDownload:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
this.createDownload(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_createDownloads:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String[] _arg0;
_arg0 = data.createStringArray();
java.lang.String[] _arg1;
_arg1 = data.createStringArray();
this.createDownloads(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_down:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.down(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_pause:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.pause(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_delete:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _result = this.delete(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_deleteAll:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.deleteAll();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_refresh:
{
data.enforceInterface(DESCRIPTOR);
this.refresh();
reply.writeNoException();
return true;
}
case TRANSACTION_getDownloadingData:
{
data.enforceInterface(DESCRIPTOR);
java.util.Map _result = this.getDownloadingData();
reply.writeNoException();
reply.writeMap(_result);
return true;
}
case TRANSACTION_startNewTask:
{
data.enforceInterface(DESCRIPTOR);
this.startNewTask();
reply.writeNoException();
return true;
}
case TRANSACTION_stopAllTask:
{
data.enforceInterface(DESCRIPTOR);
this.stopAllTask();
reply.writeNoException();
return true;
}
case TRANSACTION_getCurrentDownloadSDCardPath:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.getCurrentDownloadSDCardPath();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_setCurrentDownloadSDCardPath:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.setCurrentDownloadSDCardPath(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getDownloadFormat:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getDownloadFormat();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setDownloadFormat:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.setDownloadFormat(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getDownloadLanguage:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getDownloadLanguage();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setDownloadLanguage:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.setDownloadLanguage(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_canUse3GDownload:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.canUse3GDownload();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setCanUse3GDownload:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.setCanUse3GDownload(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_canUseAcc:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.canUseAcc();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setP2p_switch:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.setP2p_switch(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getAccPort:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.getAccPort();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_setTimeStamp:
{
data.enforceInterface(DESCRIPTOR);
long _arg0;
_arg0 = data.readLong();
this.setTimeStamp(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.youku.service.download.IDownloadService
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public void registerCallback(com.youku.service.download.ICallback callback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_registerCallback, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void unregister() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_unregister, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void createDownload(java.lang.String videoId, java.lang.String videoName) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(videoId);
_data.writeString(videoName);
mRemote.transact(Stub.TRANSACTION_createDownload, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void createDownloads(java.lang.String[] videoIds, java.lang.String[] videoNames) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStringArray(videoIds);
_data.writeStringArray(videoNames);
mRemote.transact(Stub.TRANSACTION_createDownloads, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void down(java.lang.String taskId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(taskId);
mRemote.transact(Stub.TRANSACTION_down, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
//添加一个队列到下载服务中

@Override public void pause(java.lang.String taskId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(taskId);
mRemote.transact(Stub.TRANSACTION_pause, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public boolean delete(java.lang.String taskId) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(taskId);
mRemote.transact(Stub.TRANSACTION_delete, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public boolean deleteAll() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_deleteAll, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void refresh() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_refresh, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public java.util.Map getDownloadingData() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.Map _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getDownloadingData, _data, _reply, 0);
_reply.readException();
java.lang.ClassLoader cl = (java.lang.ClassLoader)this.getClass().getClassLoader();
_result = _reply.readHashMap(cl);
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void startNewTask() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_startNewTask, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void stopAllTask() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_stopAllTask, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public java.lang.String getCurrentDownloadSDCardPath() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getCurrentDownloadSDCardPath, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void setCurrentDownloadSDCardPath(java.lang.String path) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(path);
mRemote.transact(Stub.TRANSACTION_setCurrentDownloadSDCardPath, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public int getDownloadFormat() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getDownloadFormat, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void setDownloadFormat(int format) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(format);
mRemote.transact(Stub.TRANSACTION_setDownloadFormat, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public int getDownloadLanguage() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getDownloadLanguage, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void setDownloadLanguage(int language) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(language);
mRemote.transact(Stub.TRANSACTION_setDownloadLanguage, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public boolean canUse3GDownload() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_canUse3GDownload, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void setCanUse3GDownload(boolean flag) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((flag)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_setCanUse3GDownload, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public boolean canUseAcc() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_canUseAcc, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void setP2p_switch(int value) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(value);
mRemote.transact(Stub.TRANSACTION_setP2p_switch, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public java.lang.String getAccPort() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getAccPort, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void setTimeStamp(long path) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeLong(path);
mRemote.transact(Stub.TRANSACTION_setTimeStamp, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_registerCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_unregister = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_createDownload = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_createDownloads = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_down = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_pause = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_delete = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_deleteAll = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_refresh = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_getDownloadingData = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_startNewTask = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_stopAllTask = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_getCurrentDownloadSDCardPath = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_setCurrentDownloadSDCardPath = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
static final int TRANSACTION_getDownloadFormat = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
static final int TRANSACTION_setDownloadFormat = (android.os.IBinder.FIRST_CALL_TRANSACTION + 15);
static final int TRANSACTION_getDownloadLanguage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 16);
static final int TRANSACTION_setDownloadLanguage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 17);
static final int TRANSACTION_canUse3GDownload = (android.os.IBinder.FIRST_CALL_TRANSACTION + 18);
static final int TRANSACTION_setCanUse3GDownload = (android.os.IBinder.FIRST_CALL_TRANSACTION + 19);
static final int TRANSACTION_canUseAcc = (android.os.IBinder.FIRST_CALL_TRANSACTION + 20);
static final int TRANSACTION_setP2p_switch = (android.os.IBinder.FIRST_CALL_TRANSACTION + 21);
static final int TRANSACTION_getAccPort = (android.os.IBinder.FIRST_CALL_TRANSACTION + 22);
static final int TRANSACTION_setTimeStamp = (android.os.IBinder.FIRST_CALL_TRANSACTION + 23);
}
public void registerCallback(com.youku.service.download.ICallback callback) throws android.os.RemoteException;
public void unregister() throws android.os.RemoteException;
public void createDownload(java.lang.String videoId, java.lang.String videoName) throws android.os.RemoteException;
public void createDownloads(java.lang.String[] videoIds, java.lang.String[] videoNames) throws android.os.RemoteException;
public void down(java.lang.String taskId) throws android.os.RemoteException;
//添加一个队列到下载服务中

public void pause(java.lang.String taskId) throws android.os.RemoteException;
public boolean delete(java.lang.String taskId) throws android.os.RemoteException;
public boolean deleteAll() throws android.os.RemoteException;
public void refresh() throws android.os.RemoteException;
public java.util.Map getDownloadingData() throws android.os.RemoteException;
public void startNewTask() throws android.os.RemoteException;
public void stopAllTask() throws android.os.RemoteException;
public java.lang.String getCurrentDownloadSDCardPath() throws android.os.RemoteException;
public void setCurrentDownloadSDCardPath(java.lang.String path) throws android.os.RemoteException;
public int getDownloadFormat() throws android.os.RemoteException;
public void setDownloadFormat(int format) throws android.os.RemoteException;
public int getDownloadLanguage() throws android.os.RemoteException;
public void setDownloadLanguage(int language) throws android.os.RemoteException;
public boolean canUse3GDownload() throws android.os.RemoteException;
public void setCanUse3GDownload(boolean flag) throws android.os.RemoteException;
public boolean canUseAcc() throws android.os.RemoteException;
public void setP2p_switch(int value) throws android.os.RemoteException;
public java.lang.String getAccPort() throws android.os.RemoteException;
public void setTimeStamp(long path) throws android.os.RemoteException;
}
