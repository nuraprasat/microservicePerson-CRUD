var app = angular.module('myApp', ['ngGrid','ui.bootstrap']);
                 
                   app.controller('MyCtrl', function($scope, $http, $uibModal) {
                   
                	   var headers = '';

                	   var data = "grant_type=client_credentials";

                	   var xhr = new XMLHttpRequest();
                	   xhr.withCredentials = true;

                	   xhr.addEventListener("readystatechange", function() {
                	     if(this.readyState === 4) {
                	    	 headers = { 
                	    			 headers : {
                	    				 'Authorization': 'Bearer '+JSON.parse(this.responseText).access_token
                	    			 	}
                       	    		 }
                	    	 $http.get('http://localhost:8080/api/getAllPersons', headers).
                             success(function(data) {
                            	data.forEach(obj => {
                            		obj.hobby = obj.hobby ? obj.hobby.join() : '';
                            	});
                               $scope.users = data;
                             });
                	     }
                	   });

                	   xhr.open("POST", "http://localhost:8080/oauth/token");
                	   xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                	   xhr.setRequestHeader("Authorization", "Basic bXljbGllbnQ6c2VjcmV0");

                	   xhr.send(data);
 
                     document.getElementById("errorSpan").innerHTML = "";
                     
                       
                    $scope.updateItem = function(id,person) {
                    	init($scope, $http);
                    	person.hobby = person.hobby ? person.hobby.split(',') : [];
           					$http({
							  method: 'PUT',
							  url: 'http://localhost:8080/api/updatePerson/'+id,
							  data: person,
							  headers: headers
							})
							.then(function (success) {
								person.hobby = person.hobby ? person.hobby.join() : '';
							}, function (error) {
								person.hobby = person.hobby ? person.hobby.join() : '';
							  alert("Internal server error - please try after sometime");
							});
                              
                     }
                     $scope.deleteItem = function(id) {
                    	 init($scope, $http);
                              $http.delete('http://localhost:8080/api/deletePerson/'+id,headers).
                               success(function(data) {
                                  alert('person deleted!');
                                  document.location.reload();
                              });
                     }
                     $scope.mySelections = [];
                     $scope.gridOptions = {
                             data: 'users',
                             enableRowSelection: false,
                             enableColumnResize: true,
                             enableCellEditOnFocus: true,
                             selectedItems: $scope.mySelections,
                             multiSelect: false,
                             columnDefs: [
                               { field: 'first_name', displayName: 'first name', enableCellEdit: true } ,
                               { field: 'last_name', displayName: 'last name', enableCellEdit: true} ,
                               { field: 'age', displayName: 'age', enableCellEdit: true, cellFilter: 'number'} ,
                               { field: 'favourite_colour', displayName: 'favourite colour', enableCellEdit: true} ,
                               { field: 'hobby', displayName: 'hobby', enableCellEdit: true} ,
                               { field:'', displayName: 'Update', enableCellEdit: false,
                                  cellTemplate: '<button id="editBtn" type="button"  ng-click="updateItem(row.entity.person_id,row.entity)" >Update</button>'},
                               { field:'', displayName: 'Delete', enableCellEdit: false, 
                                  cellTemplate: '<button id="editBtn" type="button"  ng-click="deleteItem(row.entity.person_id)" >Delete</button>'}
                              ]
                               
                           };
                           
                      $scope.open = function() {
					    var modalInstance =  $uibModal.open({
					      templateUrl: "popup.html",
					      controller: "ModalContentCtrl",
					      size: '',
					    });
					  }              
  });

    app.controller('ModalContentCtrl', function($scope, $http, $uibModalInstance) {
    $scope.saveDetail = function () {
    	init($scope, $http);
    	var headers;
    	var hobbys = $scope.hobby.split(',');
    	var person = {first_name:$scope.firstName, last_name:$scope.lastName, age: $scope.age, favourite_colour: $scope.colour, hobby: hobbys}
			$http({
			  method: 'POST',
			  url: 'http://localhost:8080/api/createPerson/',
			  data: person,
			  headers: $scope.headers
			})
			.then(function (success) {
			  alert('update completed!');
			  $uibModalInstance.dismiss();
			  document.location.reload();
			}, function (error) {
				alert("Internal server error - please try after sometime");
			});
                              
        }
   
  $scope.cancel = function(){
    $uibModalInstance.dismiss();
  } 
  
});
    
    function init($scope, $http) {
    	var data = "grant_type=client_credentials";

    	var xhr = new XMLHttpRequest();
    	xhr.withCredentials = true;

	   xhr.addEventListener("readystatechange", function() {
	     if(this.readyState === 4) {
	    	 $scope.headers = { 
	    			 headers : {
	    				 'Authorization': 'Bearer '+JSON.parse(this.responseText).access_token
	    			 	}
       	    		 }
	     } else if(this.status === 500) {
	    	 alert("Internal server error - please try after sometime");
	     }
	   });

	   xhr.open("POST", "http://localhost:8080/oauth/token");
	   xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	   xhr.setRequestHeader("Authorization", "Basic bXljbGllbnQ6c2VjcmV0");

	   xhr.send(data);
    }    
                   