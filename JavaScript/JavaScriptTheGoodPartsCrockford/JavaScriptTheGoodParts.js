function identity(x) {
	return x;
}

function add(x, y) {
	return x + y;
}

function mul(x, y) {
	return x * y;
}

function identityf(x) {
	return function() {
		return x;
	}
}

function addf(x) {
	return function(y) {
		return x + y;
	}
}

function applyf(binary) {
	return function (x) {
		return function (y) {
			return binary(x, y);
		}
	}
}

function curry(func, x) {
	return function (y) {
		return func(x, y);
	}
}

function methodize(func) {
	return function(y) {
		return func(this, y);
	}
}

function demethodize(func) {
	return function (that, y) {
		return func.call(that, y);
	}
}

function twice(binfunc) {
	return function (a) {
		return binfunc(a, a);
	}
}

function composeu(f, g) {
	return function(x) {
		return g(f(x));
	}
}

function composeb(f, g) {
	return function(a, b, c) {
		return g(f(a, b), c);
	}
}
		
		
function once(func) {
	return function() {
		var f = func;
		func = null;
		return f.apply(this, arguments);
	}
}

// returns a object containing two functions
function counterf(x) {
	var myCount = x;
	return {
		inc: function () {
			myCount += 1;
			return myCount;
		},
		dec: function () {
			myCount -= 1;
			return myCount;
		}
	};
}

function revocable(nice) {
	return {
		invoke: function() {
			return nice.apply(this, arguments);
		},
		revoke: function() {
			nice = null;
		}
	};
}
// ***********************************************************


console.log(add(3,4));
console.log(mul(3,4));

idf = identityf(3);
console.log(idf());

console.log(addf(3)(4));

console.log(applyf(add)(3)(4));
console.log(applyf(mul)(3)(4));

add3 = curry(add, 3);
console.log(add3(4));

console.log(curry(mul,5)(6));

// without writing any new functions, show three ways to create inc function
var inc = addf(1);
console.log(inc(5));

inc = applyf(add)(1);
console.log(inc(5));

inc = curry(add, 1);
console.log(inc(5));

// Write methodize, a function that converts a binary function to a method.
Number.prototype.add = methodize(add);
console.log((3).add(4));

// Write demethodize, a function that converts a method to a binary function.
console.log(demethodize(Number.prototype.add)(5, 6));

var double = twice(add);
console.log(double(11));

var square = twice(mul);
console.log(square(11));

console.log(composeu(double, square)(3));

console.log(composeb(add,mul)(2, 3, 5));

// write a function that allows another function to be called only once
var add_once = once(add);
console.log(add_once(3, 4)); // 7
//add_once(3, 4); // throw!

// write a factory function that returns two functions that implement
// up/down counter
var counter = counterf(10);
console.log(counter.inc());
console.log(counter.dec());

// the below do not work...alert is not allowing apply to be called on it..tried Chrome and Firefox

var temp = revocable(alert);
temp.invoke(7);
temp.revoke();
temp.invoke(8);

