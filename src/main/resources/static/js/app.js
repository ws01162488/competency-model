angular.module('competency-app', [ 'toaster' ]);

function indexCtrl($scope, $http, $timeout, toaster) {
	$scope.domains = [];
	$scope.competencys = [];
	$scope.loadDomains = function() {
		$http.get('getDomains').then(function(response) {
			angular.copy(response.data, $scope.domains);
		});
	};
	$scope.loadDomains();
	
	$scope.checkDomain = function(domain){
		domain.check = !domain.check;
		if(domain.check){
			$scope.competencys = domain.competencys;
		} else {
			$scope.competencys = [];
		}
	};
};