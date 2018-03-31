var competencyCtrls = angular.module('competencyCtrls', []);
competencyCtrls.controller('domainCtrl', function ($scope, $http, $location,myFactory) {
	$scope.domains = [];
	$scope.competencys = [];
	$scope.checkedCompetencys = myFactory.get();
	console.log(myFactory.get());
	
	$scope.loadDomains = function() {
		$http.get('/getDomains').then(function(response) {
			angular.copy(response.data, $scope.domains);
		});
	};
	$scope.loadDomains();

	$scope.checkDomain = function(domain) {
		domain.check = !domain.check;
		$scope.competencys = domain.competencys;
		angular.forEach(domain.competencys, function(competency){
			if(domain.check){
				$scope.checkedCompetencys.set(competency.id,competency);
			} else {
				$scope.checkedCompetencys.delete(competency.id);
			}
			
		});
	};
	
	$scope.next = function(){
		myFactory.set($scope.checkedCompetencys);
		$location.url("/class");
	};
});
competencyCtrls.controller('classCtrl', function ($scope,$location,myFactory) {
	$scope.prev = function(){
		$location.url("/domain");
	};
});