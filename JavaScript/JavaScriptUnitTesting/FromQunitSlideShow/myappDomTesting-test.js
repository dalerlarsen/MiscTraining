module("jQuery#enumerate");
 
test("chainable", 1, function() {
  var items = $("#qunit-fixture li");
  strictEqual(items.enumerate(), items, "should be chaninable");
});
 
test("no args passed", 3, function() {
  var items = $("#qunit-fixture li").enumerate();
  equal(items.eq(0).text(), "1. foo", "first item should have index 1");
  equal(items.eq(1).text(), "2. bar", "second item should have index 2");
  equal(items.eq(2).text(), "3. baz", "third item should have index 3");
});
 
test("0 passed", 3, function() {
  var items = $("#qunit-fixture li").enumerate(0);
  equal(items.eq(0).text(), "0. foo", "first item should have index 0");
  equal(items.eq(1).text(), "1. bar", "second item should have index 1");
  equal(items.eq(2).text(), "2. baz", "third item should have index 2");
});
 
test("1 passed", 3, function() {
  var items = $("#qunit-fixture li").enumerate(1);
  equal(items.eq(0).text(), "1. foo", "first item should have index 1");
  equal(items.eq(1).text(), "2. bar", "second item should have index 2");
  equal(items.eq(2).text(), "3. baz", "third item should have index 3");
});

module("core");

test("a test in the core module", function() {
  ok(true, "this test had better pass");
});
 
test("another test in the core module", function() {
  ok(true, "this test had also better pass");
});
 
module("options");
 
test("a test in the options module", function() {
  ok(true, "this test really, really better pass");
});
 
test("another test in the options module", function() {
  ok(false, "sadly, this test is going to fail");
});


//Defining a "setup" callback.

module("module1", {
  setup: function() {
    ok(true, "once extra assert per test");
  }
});
 
test("test with setup", function() {
  expect(1);
});
 
 
// Defining both "setup" and "teardown" callbacks.
 
module("module2", {
  setup: function() {
    ok(true, "once extra assert per test");
    this.prop = "foo";
  },
  teardown: function() {
    ok(true, "and one extra assert after each test");
  }
});
 
test("test with setup and teardown", function() {
  expect(3);
  deepEqual(this.prop, "foo", "this.prop === 'foo' in all tests");
});

// Async testing
module("AsyncTesting");

// use stop and start
test("expectations", function() {
	  expect(1);
	 
	  var actual = false;
	 
	  stop();
	  setTimeout(function() {
	    ok(actual, "this test actually runs, and fails");
	    start();
	  }, 1000);
});

// use asyncTest and start
asyncTest("asyncTest & start", function() {
	  expect(1);
	 
	  var actual = false;
	 
	  setTimeout(function() {
	    ok(actual, "this test actually runs, and fails");
	    start();
	  }, 1000);
});	

// stops and starts making a getJSON call
test("stops & starts", function() {
	  expect(4);
	 
	  var url = "http://jsfiddle.net/echo/jsonp/?callback=?";
	 
	  stop();
	  $.getJSON(url, {a: 1}, function(data) {
	    ok(data, "data is returned from the server");
	    equal(data.a, "1", "the value of data.a should be 1");
	    start();
	  });
	 
	  stop();
	  $.getJSON(url, {b: 2}, function(data) {
	    ok(data, "data is returned from the server");
	    equal(data.b, "2", "the value of data.b should be 2");
	    start();
	  });
});

// Mocking AJAX - if you mock your AJAX requests, you actually test your JavaScript and not your server

// Simulate your API.
$.mockAjax("json", {
    "/user": {status: -1},
    "/user/(\\d+)": function(matches) {
        return {status: 1, user: "sample user " + matches[1]};
    }
});

// Unit tests.

test("user tests", function() {
    expect(5);

    stop();
    $.getJSON("/user", function(data) {
        ok(data, "data is returned from the server");
        equal(data.status, "-1", "no user specified, status should be -1");
        start();
    });

    stop();
    //noinspection JSUnresolvedFunction
    $.getJSON("/user/123", function(data) {
        ok(data, "data is returned from the server");
        equal(data.status, "1", "user found, status should be 1");
        equal(data.user, "sample user 123", "user found, id should be 123");
        start();
    });
});
