angular.module('competency-app', [ 'toaster' ]);

function indexCtrl($scope, $http, $timeout, toaster) {
	$scope.domains = [];
	$scope.competencys = [];
	$scope.checkedCompetencys = new Map();
	$scope.loadDomains = function() {
		$http.get('getDomains').then(function(response) {
			angular.copy(response.data, $scope.domains);
		});
	};
	$scope.loadDomains();
	
	$scope.checkDomain = function(domain){
		domain.check = !domain.check;
		$scope.competencys = domain.check ? domain.competencys: [];
	};
};