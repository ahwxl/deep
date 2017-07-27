'use strict';
/*
var formId = $("#myform input[name=startFormId]").val();

$.ajax({
	url:cxt+'/form/queryForm',
	async:false,
	method:'POST',
	data:'formId='+formId,
	dataType : 'json',
	error :function(resp){alert(resp)},
	success:function(resp){
	    if(resp != null){
	       $("#startFormContent").html(resp.formContent);
	    }
	}
});*/

var taskcompleteApp = angular.module('taskcompleteApp', []);

taskcompleteApp.controller('TaskCompleteController', function TaskCompleteController($scope,$http) {

	  var self = this;
	  var procDef = {};

	  $scope.formInfo = procDef;
	  
	  $scope.processInstanceId = $("#myform input[name=processInstanceId]").val();
	  var formId = $("#myform input[name=startFormId]").val();
	  
	  $http.get(cxt+'/form/queryForm?formId='+formId).then(function(rsp) {
	        //$scope.formInfo = rsp.data;
	        $("#startFormContent").html(rsp.data.formContent);
	  });
	  
	  $http.get(cxt+'/bpm/queryFormValuesByProcessId?processInstanceId='+$scope.processInstanceId).then(function(rsp) {
	        $scope.formInfo = rsp.data;
	  });
	  
});