package com.example.deathdevilt_t.googlemaps_testv1.DBContext;

import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.ConflictScheduleObject;
import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.Log.Model.LogNode;
import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.NodeChargeObject;
import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.NodeObject;
import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.NodeSessionObject;
import com.example.deathdevilt_t.googlemaps_testv1.ObjectClass.UserInformationObject;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by DeathDevil.T_T on 19-Jun-17.
 */

public class DBContext  {
    private Realm realm;

    private DBContext() {
        realm = Realm.getDefaultInstance();
    }

    private static DBContext inst;

    public static DBContext getInst() {
        if (inst == null) {
            inst = new DBContext();
        }
        return inst;
    }

    public void addNodeObject(NodeObject model) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(model);
        realm.commitTransaction();
    }
    public void DeleteNodeObject(){
        realm.beginTransaction();
        RealmResults<NodeObject> results = realm.where(NodeObject.class).findAll();
        results.deleteAllFromRealm();
        realm.commitTransaction();
    }
    public List<NodeObject> getAllNode() {
        return realm.where(NodeObject.class).findAll();
    }
    public NodeObject getNodeObjectByPhoneNumber(String phone) {
        return realm.where(NodeObject.class).equalTo("phone",phone).findFirst();
    }
    public UserInformationObject getNodeByPhoneNumber(String phone) {
        return realm.where(UserInformationObject.class).equalTo("phone",phone).findFirst();
    }
    public void addUserInformation(UserInformationObject model) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(model);
        realm.commitTransaction();
    }
    public void DeleteUserInformation(){
        realm.beginTransaction();
        RealmResults<UserInformationObject> results = realm.where(UserInformationObject.class).findAll();
        results.deleteAllFromRealm();
        realm.commitTransaction();
    }
    public List<UserInformationObject> getAllUserInformation() {
        return realm.where(UserInformationObject.class).findAll();
    }

    public UserInformationObject getUserInformationByEmailAddress(String emailAddress) {
        return realm.where(UserInformationObject.class).equalTo("emailAddress",emailAddress).findFirst();
    }
    public void addLogNode(LogNode model) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(model);
        realm.commitTransaction();
    }
    public List<LogNode> getAllLogNode() {
        return realm.where(LogNode.class).findAll();
    }
    public List<LogNode> getAllLogNodeByNotSync(String phoneNumber) {
        return realm.where(LogNode.class).equalTo("isSync","0").equalTo("targetNode",phoneNumber).findAll();
    }
    public List<LogNode> getAllLogNodeOkSync(String phoneNumber) {
        return realm.where(LogNode.class).equalTo("isSync","1").equalTo("targetNode",phoneNumber).findAll();
    }
    public void updateSyncOkLogNodeByIdNode(int idNode, String phoneNumber){
        LogNode logNode = realm.where(LogNode.class).equalTo("idLogNode",idNode).equalTo("targetNode",phoneNumber).findFirst();
        realm.beginTransaction();
        if(logNode!=null){
            logNode.setIsSync("1");
        }
        realm.commitTransaction();
    }
    public void updateIsOnlineLogNodeByIdNode(int idNode, String phoneNumber){
        LogNode logNode = realm.where(LogNode.class).equalTo("idLogNode",idNode).equalTo("targetNode",phoneNumber).findFirst();
        realm.beginTransaction();
        if(logNode!=null){
            //0 is offline
            logNode.setIsOnline(0);
        }
        realm.commitTransaction();
    }
    public void DeleteAllLogNodeSyncOk(){
        realm.beginTransaction();
        RealmResults<LogNode> results = realm.where(LogNode.class).equalTo("isSync","1").findAll();
        results.deleteAllFromRealm();
        realm.commitTransaction();
    }
    public void DeleteAllLogNode(){
        realm.beginTransaction();
        RealmResults<LogNode> results = realm.where(LogNode.class).findAll();
        results.deleteAllFromRealm();
        realm.commitTransaction();
    }
    public void DeleteLogNodeById(String id){
        realm.beginTransaction();
        RealmResults<LogNode> results = realm.where(LogNode.class).equalTo("idLogNode",id).findAll();
        results.deleteAllFromRealm();
        realm.commitTransaction();
    }
    public NodeSessionObject getNodeSessionByPhoneNumber(String phoneNumber){
        return realm.where(NodeSessionObject.class).equalTo("phoneNumber",phoneNumber).findFirst();
    }
    public List<NodeSessionObject> getAllNodeSession(){
        return realm.where(NodeSessionObject.class).findAll();
    }
    public void deleteNodeSessionByPhoneNumberAndIdLog(String phoneNumber,int idLogNode){
        realm.beginTransaction();
        RealmResults<NodeSessionObject> results = realm.where(NodeSessionObject.class).equalTo("phoneNumber",phoneNumber).equalTo("idLogNode",idLogNode).findAll();
        results.deleteAllFromRealm();
        realm.commitTransaction();
    }
    public void DeleteAllNodeSession(){
        realm.beginTransaction();
        RealmResults<NodeSessionObject> results = realm.where(NodeSessionObject.class).findAll();
        results.deleteAllFromRealm();
        realm.commitTransaction();
    }
    public void addNodeSession(NodeSessionObject model) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(model);
        realm.commitTransaction();
    }
    public List<NodeChargeObject> getAllNodeCharge(){
        return realm.where(NodeChargeObject.class).findAll();
    }
    public void deleteNodeChargeByPhoneNumber(String phoneNumber){
        realm.beginTransaction();
        RealmResults<NodeChargeObject> results = realm.where(NodeChargeObject.class).equalTo("phoneNumber",phoneNumber).findAll();
        results.deleteAllFromRealm();
        realm.commitTransaction();
    }
    public void DeleteAllNodeCharge(){
        realm.beginTransaction();
        RealmResults<NodeChargeObject> results = realm.where(NodeChargeObject.class).findAll();
        results.deleteAllFromRealm();
        realm.commitTransaction();
    }
    public void addNodeCharge(NodeChargeObject model) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(model);
        realm.commitTransaction();
    }
    public NodeChargeObject getNodeChargeByPhoneNumber(String phoneNumber){
        return realm.where(NodeChargeObject.class).equalTo("phoneNumber",phoneNumber).findFirst();
    }
    public void addConflictSchedule(ConflictScheduleObject model) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(model);
        realm.commitTransaction();
    }
    public void DeleteAllConflictSchedule(){
        realm.beginTransaction();
        RealmResults<ConflictScheduleObject> results = realm.where(ConflictScheduleObject.class).findAll();
        results.deleteAllFromRealm();
        realm.commitTransaction();
    }
    public List<ConflictScheduleObject> getAllConflictSchedule(){
        return realm.where(ConflictScheduleObject.class).findAll();
    }
    public void updateConflictScheduleByIdAndPhoneNumber( String phoneNumber,String duration,String hour,String minute){
        ConflictScheduleObject conflictScheduleObject = realm.where(ConflictScheduleObject.class).equalTo("phoneNumber",phoneNumber).findFirst();
        realm.beginTransaction();
        if(duration!=null){
            conflictScheduleObject.setDuration(duration);
        }else if(hour!=null){
            conflictScheduleObject.setHour(hour);
        }else if(minute!=null){
            conflictScheduleObject.setMinute(minute);
        }
        realm.commitTransaction();
    }
    public ConflictScheduleObject getConflictScheduleObject(String phoneNumber){
        return realm.where(ConflictScheduleObject.class).equalTo("phoneNumber",phoneNumber).findFirst();
    }

}
