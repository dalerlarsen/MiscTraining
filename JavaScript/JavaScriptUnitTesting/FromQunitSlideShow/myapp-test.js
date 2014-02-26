test("ok", 3, function() {
  ok(true,  "passes because true is true");
  ok(1,     "passes because 1 is truthy");
  ok("",    "fails because empty string is not truthy");
});

test("equal", 3, function() {
	  var actual = 5 - 4;
	 
	  equal(actual, 1,     "passes because 1 == 1");
	  equal(actual, true,  "passes because 1 == true");
	  equal(actual, false, "fails because 1 != false");
});

test("notEqual", 3, function() {
	  var actual = 5 - 4;
	 
	  notEqual(actual, 0,     "passes because 1 != 0");
	  notEqual(actual, false, "passes because 1 != false");
	  notEqual(actual, true,  "fails because 1 == true");
});

test("strictEqual", 3, function() {
	  var actual = 5 - 4;
	 
	  strictEqual(actual, 1,     "passes because 1 === 1");
	  strictEqual(actual, true,  "fails because 1 !== true");
	  strictEqual(actual, false, "fails because 1 !== false");
});

test("notStrictEqual", 3, function() {
	  var actual = 5 - 4;
	 
	  notStrictEqual(actual, 1,     "fails because 1 === 1");
	  notStrictEqual(actual, true,  "passes because 1 !== true");
	  notStrictEqual(actual, false, "passes because 1 !== false");
});	

test("deepEqual", 7, function() {
	  var actual = {a: 1};
	 
	  equal(    actual, {a: 1},   "fails because objects are different");
	  deepEqual(actual, {a: 1},   "passes because objects are equivalent");
	  deepEqual(actual, {a: "1"}, "fails because '1' !== 1");
	 
	  var a = $("body > *");
	  var b = $("body").children();
	 
	  equal(    a,       b,       "fails because jQuery objects are different");
	  deepEqual(a,       b,       "fails because jQuery objects are not equivalent");
	  equal(    a.get(), b.get(), "fails because element arrays are different");
	  deepEqual(a.get(), b.get(), "passes because element arrays are equivalent");
});

test("notDeepEqual", 3, function() {
	  var actual = {a: 1};
	 
	  notEqual(    actual, {a: 1},   "passes because objects are different");
	  notDeepEqual(actual, {a: 1},   "fails because objects are equivalent");
	  notDeepEqual(actual, {a: "1"}, "passes because '1' !== 1");
});

test("throws", 3, function() {
	  throws(function() {
	    throw new Error("Look ma, I'm an error!");
	  }, "passes because an error is thrown inside the callback");
	 
	  throws(function() {
	    x; // ReferenceError: x is not defined
	  }, "passes because an error is thrown inside the callback");
	 
	  throws(function() {
	    var a = 1;
	  }, "fails because no error is thrown inside the callback");
});


// Tests should be atomic
// Don't do this. Execution order cannot be guaranteed

var counter = 0;
 
test("first test", 1, function() {
  counter++;
  equal(counter, 1, "counter should be 1");
});
 
test("second test", 1, function() {
  counter++;
  equal(counter, 2, "counter should be 2");
});
 
test("third test", 2, function() {
  counter++;
  equal(counter, 2, "counter should be 2");
  ok(false, "oops, an error");
});

// end Don't do this