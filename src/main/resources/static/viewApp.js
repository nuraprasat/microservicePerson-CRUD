var app = angular.module('myApp', ['ngGrid','ui.bootstrap']);
                 
                   app.controller('MyCtrl', function($scope, $http, $uibModal) {
                     document.getElementById("errorSpan").innerHTML = "";
                     $http.get('http://localhost:8080/getAllPersons').
                     success(function(data) {
                       $scope.users = data;
                     });
                       
                    $scope.saveItem = function(id,person) {
           					$http({
							  method: 'PUT',
							  url: 'http://localhost:8080/updatePerson/1'+id,
							  data: person
							})
							.then(function (success) {
							  alert('update completed!');
							}, function (error) {
							  document.getElementById("errorSpan").innerHTML="person not found";
							});
                              
                     }
                     $scope.deleteItem = function(name,surname,address) {
                              $http.post('http://localhost:8080/rest-angular/rest/simple',{name: name,surname: surname,address: address}).
                               success(function(data) {
                                  alert('update completed!');
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
                                  cellTemplate: '<button id="editBtn" type="button"  ng-click="saveItem(row.entity.person_id,row.entity)" >Update</button>'},
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

    app.controller('ModalContentCtrl', function($scope, $uibModalInstance) {

  $scope.ok = function(){
    $uibModalInstance.close("Ok");
  }
   
  $scope.cancel = function(){
    $uibModalInstance.dismiss();
  } 
  
});
                   