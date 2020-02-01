var app = angular.module('myApp', ['ngGrid']);
                 
                   app.controller('MyCtrl', ['$scope', '$http', '$filter', function($scope, $http, $filter) {
                     
                     $http.get('http://localhost:8080/getAllPersons').
                     success(function(data) {
                       $scope.users = data;
                     });
                       
                    $scope.saveItem = function(name,surname,address) {
                              $http.post('http://localhost:8080/rest-angular/rest/simple',{name: name,surname: surname,address: address}).
                               success(function(data) {
                                  alert('update completed!');
                              });
                     }
                     
                     $scope.gridOptions = {
                             data: 'users',
                             enableRowSelection: false,
                             enableColumnResize: true,
                             enableCellEditOnFocus: true,
                             multiSelect: false,
                             columnDefs: [
                               { field: 'first_name', displayName: 'first name', enableCellEdit: false } ,
                               { field: 'last_name', displayName: 'last name', enableCellEdit: false} ,
                               { field: 'age', displayName: 'age', enableCellEdit: true} ,
                               { field: 'favourite_colour', displayName: 'favourite colour', enableCellEdit: true} ,
                               { field: 'hobby', displayName: 'hobby', enableCellEdit: true} ,
                               { field:'', displayName: 'Save', enableCellEdit: false,
                                  cellTemplate: '<button id="editBtn" type="button"  ng-click="saveItem(row.entity.name, row.entity.surname,row.entity.address)" >Save</button>'}
                              ]
                               
                           };
                      
                     
                     }]);
                   