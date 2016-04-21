/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: E:\\GitHub\\Dota2Helper\\youkuPlayerOpenSDK\\src\\main\\aidl\\com\\youku\\service\\download\\ICallback.aidl
 */
package com.youku.service.download;
/**
 * ICallback.下载状态改变的回掉
 * 
 * @author 刘仲男 qq81595157@126.com
 * @version v3.5
 * @created time 2013-10-5 下午1:16:02
 */
public interface ICallback extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.youku.service.download.ICallback
{
private static final java.lang.String DESCRIPTOR = "com.youku.service.download.ICallback";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.youku.service.download.ICallback interface,
 * generating a proxy if needed.
 */
public static com.youku.service.download.ICallback asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.youku.service.download.ICallback))) {
return ((com.youku.service.download.ICallback)iin);
}
return new com.youku.service.download.ICallback.Stub.Proxy(obj);
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
case TRANSACTION_onChanged:
{
data.enforceInterface(DESCRIPTOR);
com.youku.service.download.DownloadInfo _arg0;
if ((0!=data.readInt())) {
_arg0 = com.youku.service.download.DownloadInfo.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
this.onChanged(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_onFinish:
{
data.enforceInterface(DESCRIPTOR);
com.youku.service.download.DownloadInfo _arg0;
if ((0!=data.readInt())) {
_arg0 = com.youku.service.download.DownloadInfo.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
this.onFinish(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_refresh:
{
data.enforceInterface(DESCRIPTOR);
this.refresh();
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.youku.service.download.ICallback
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
@Override public void onChanged(com.youku.service.download.DownloadInfo info) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((info!=null)) {
_data.writeInt(1);
info.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_onChanged, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void onFinish(com.youku.service.download.DownloadInfo info) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((info!=null)) {
_data.writeInt(1);
info.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_onFinish, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
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
}
static final int TRANSACTION_onChanged = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_onFinish = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_refresh = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
}
public void onChanged(com.youku.service.download.DownloadInfo info) throws android.os.RemoteException;
public void onFinish(com.youku.service.download.DownloadInfo info) throws android.os.RemoteException;
public void refresh() throws android.os.RemoteException;
}
