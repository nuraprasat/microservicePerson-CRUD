var app = angular.module('myApp', ['ngGrid','ui.bootstrap']);
                 
                   app.controller('MyCtrl', function($scope, $http, $uibModal) {
                   


        var url = 'http://localhost:8080/oauth/token';

        var username = 'myclient'; // Username of PowerBI "pro" account - stored in config
        var password = 'secret'; // Password of PowerBI "pro" account - stored in config

    var headers = {
            'Content-Type': 'application/x-www-form-urlencoded',
            'Authorization': 'Basic bXljbGllbnQ6c2VjcmV0'
        };

        var formData = {
            grant_type: 'client_credentials',
        };

       $http({
			  method: 'POST',
			  url: url,
			  data: formData,
			  headers: headers
			})
			.then(function (success) {
			  alert('update completed!');
			  document.location.reload();
			}, function (error) {
			  document.getElementById("errorSpan").innerHTML="person not found";
			});

        
 
                     document.getElementById("errorSpan").innerHTML = "";
                     $http.get('http://localhost:8080/getAllPersons').
                     success(function(data) {
                       $scope.users = data;
                     });
                       
                    $scope.updateItem = function(id,person) {
           					$http({
							  method: 'PUT',
							  url: 'http://localhost:8080/updatePerson/'+id,
							  data: person
							})
							.then(function (success) {
							  alert('update completed!');
							  document.location.reload();
							}, function (error) {
							  document.getElementById("errorSpan").innerHTML="person not found";
							});
                              
                     }
                     $scope.deleteItem = function(name,surname,address) {
                              $http.post('http://localhost:8080/rest-angular/rest/simple',{name: name,surname: surname,address: address}).
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
                               { field: 'age', displayName: 'age', enableCellEdit: true} ,
                               { field: 'favourite_colour', displayName: 'favourite colour', enableCellEdit: true} ,
                               { field: 'hobby', displayName: 'hobby', enableCellEdit: true} ,
                               { field:'', displayName: 'Update', enableCellEdit: false,
                                  cellTemplate: '<button id="editBtn" type="button"  ng-click="updateItem(row.entity.person_id,row.entity)" >Update</button>'},
                               { field:'', displayName: 'Delete', enableCellEdit: false, 
                                  cellTemplate: '<button id="editBtn" type="button"  ng-click="deleteItem(row.entity,row.entity.name, row.entity.surname,row.entity.address)" >Delete</button>'}
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
    var hobbys = $scope.hobby.split(',');
    var person = {first_name:$scope.firstName, last_name:$scope.lastName, age: $scope.age, favourite_colour: $scope.colour, hobby: hobbys}
			$http({
			  method: 'POST',
			  url: 'http://localhost:8080/createPerson/',
			  data: person
			})
			.then(function (success) {
			  alert('update completed!');
			  $uibModalInstance.dismiss();
			  document.location.reload();
			}, function (error) {
			  document.getElementById("errorSpan").innerHTML="person not found";
			});
                              
        }
   
  $scope.cancel = function(){
    $uibModalInstance.dismiss();
  } 
  
});
                   