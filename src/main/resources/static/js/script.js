$(document).ready(function () {


    // Fast fix bor position issue with Propper.js
    // Will be fixed in Bootstrap 4.1 - https://github.com/twbs/bootstrap/pull/24092
    Popper.Defaults.modifiers.computeStyle.gpuAcceleration = false;


    // Add body-small class if window less than 768px
    if ($(window).width() < 769) {
        $('body').addClass('body-small')
    } else {
        $('body').removeClass('body-small')
    }

    // MetisMenu
    var sideMenu = $('#side-menu').metisMenu();

    // Collapse ibox function
    $('.collapse-link').on('click', function (e) {
        e.preventDefault();
        var ibox = $(this).closest('div.ibox');
        var button = $(this).find('i');
        var content = ibox.children('.ibox-content');
        content.slideToggle(200);
        button.toggleClass('fa-chevron-up').toggleClass('fa-chevron-down');
        ibox.toggleClass('').toggleClass('border-bottom');
        setTimeout(function () {
            ibox.resize();
            ibox.find('[id^=map-]').resize();
        }, 50);
    });

    // Close ibox function
    $('.close-link').on('click', function (e) {
        e.preventDefault();
        var content = $(this).closest('div.ibox');
        content.remove();
    });

    // Fullscreen ibox function
    $('.fullscreen-link').on('click', function (e) {
        e.preventDefault();
        var ibox = $(this).closest('div.ibox');
        var button = $(this).find('i');
        $('body').toggleClass('fullscreen-ibox-mode');
        button.toggleClass('fa-expand').toggleClass('fa-compress');
        ibox.toggleClass('fullscreen');
        setTimeout(function () {
            $(window).trigger('resize');
        }, 100);
    });

    // Close menu in canvas mode
    $('.close-canvas-menu').on('click', function (e) {
        e.preventDefault();
        $("body").toggleClass("mini-navbar");
        SmoothlyMenu();
    });

    // Run menu of canvas
    $('body.canvas-menu .sidebar-collapse').slimScroll({
        height: '100%',
        railOpacity: 0.9
    });

    // Open close right sidebar
    $('.right-sidebar-toggle').on('click', function (e) {
        e.preventDefault();
        $('#right-sidebar').toggleClass('sidebar-open');
    });

    // Initialize slimscroll for right sidebar
    $('.sidebar-container').slimScroll({
        height: '100%',
        railOpacity: 0.4,
        wheelStep: 10
    });

    // Open close small chat
    $('.open-small-chat').on('click', function (e) {
        e.preventDefault();
        $(this).children().toggleClass('fa-comments').toggleClass('fa-times');
        $('.small-chat-box').toggleClass('active');
    });

    // Initialize slimscroll for small chat
    $('.small-chat-box .content').slimScroll({
        height: '234px',
        railOpacity: 0.4
    });

    // Small todo handler
    $('.check-link').on('click', function () {
        var button = $(this).find('i');
        var label = $(this).next('span');
        button.toggleClass('fa-check-square').toggleClass('fa-square-o');
        label.toggleClass('todo-completed');
        return false;
    });


    // Minimalize menu
    $('.navbar-minimalize').on('click', function (event) {
        event.preventDefault();
        $("body").toggleClass("mini-navbar");
        SmoothlyMenu();

    });

    // Tooltips demo
    $('.tooltip-demo').tooltip({
        selector: "[data-toggle=tooltip]",
        container: "body"
    });


    // Move right sidebar top after scroll
    $(window).scroll(function () {
        if ($(window).scrollTop() > 0 && !$('body').hasClass('fixed-nav')) {
            $('#right-sidebar').addClass('sidebar-top');
        } else {
            $('#right-sidebar').removeClass('sidebar-top');
        }
    });

    $("[data-toggle=popover]")
        .popover();

    // Add slimscroll to element
    $('.full-height-scroll').slimscroll({
        height: '100%'
    })
});

// Minimalize menu when screen is less than 768px
$(window).bind(" resize", function () {
    if ($(this).width() < 769) {
        $('body').addClass('body-small')
    } else {
        $('body').removeClass('body-small')
    }
});

// Fixed Sidebar
$(window).bind("load", function () {
    if ($("body").hasClass('fixed-sidebar')) {
        $('.sidebar-collapse').slimScroll({
            height: '100%',
            railOpacity: 0.9
        });
    }
});



// Minimalize menu when screen is less than 768px
$(window).bind("load resize", function () {
    if ($(this).width() < 769) {
        $('body').addClass('body-small')
    } else {
        $('body').removeClass('body-small')
    }
});

// Local Storage functions
// Set proper body class and plugins based on user configuration
$(document).ready(function () {
    if (localStorageSupport()) {

        var collapse = localStorage.getItem("collapse_menu");
        var fixedsidebar = localStorage.getItem("fixedsidebar");
        var fixednavbar = localStorage.getItem("fixednavbar");
        var boxedlayout = localStorage.getItem("boxedlayout");
        var fixedfooter = localStorage.getItem("fixedfooter");

        var body = $('body');

        if (fixedsidebar == 'on') {
            body.addClass('fixed-sidebar');
            $('.sidebar-collapse').slimScroll({
                height: '100%',
                railOpacity: 0.9
            });
        }

        if (collapse == 'on') {
            if (body.hasClass('fixed-sidebar')) {
                if (!body.hasClass('body-small')) {
                    body.addClass('mini-navbar');
                }
            } else {
                if (!body.hasClass('body-small')) {
                    body.addClass('mini-navbar');
                }

            }
        }

        if (fixednavbar == 'on') {
            $(".navbar-static-top").removeClass('navbar-static-top').addClass('navbar-fixed-top');
            body.addClass('fixed-nav');
        }

        if (boxedlayout == 'on') {
            body.addClass('boxed-layout');
        }

        if (fixedfooter == 'on') {
            $(".footer").addClass('fixed');
        }
    }

    //handle menu
    if ($("#menuname").length){
        var couserid = GetStorage("userid");
        if(IsNull(couserid) == false){
            $("#menuname").text(couserid);
        }
    }
});

// check if browser support HTML5 local storage
function localStorageSupport() {
    return (('localStorage' in window) && window['localStorage'] !== null)
}

// For demo purpose - animation css script
function animationHover(element, animation) {
    element = $(element);
    element.hover(
        function () {
            element.addClass('animated ' + animation);
        },
        function () {
            //wait for animation to finish before removing classes
            window.setTimeout(function () {
                element.removeClass('animated ' + animation);
            }, 2000);
        });
}

function SmoothlyMenu() {
    if (!$('body').hasClass('mini-navbar') || $('body').hasClass('body-small')) {
        // Hide menu in order to smoothly turn on when maximize menu
        $('#side-menu').hide();
        // For smoothly turn on menu
        setTimeout(
            function () {
                $('#side-menu').fadeIn(400);
            }, 200);
    } else if ($('body').hasClass('fixed-sidebar')) {
        $('#side-menu').hide();
        setTimeout(
            function () {
                $('#side-menu').fadeIn(400);
            }, 100);
    } else {
        // Remove all inline style from jquery fadeIn function to reset menu state
        $('#side-menu').removeAttr('style');
    }
}

// Dragable panels
function WinMove() {
    var element = "[class*=col]";
    var handle = ".ibox-title";
    var connect = "[class*=col]";
    $(element).sortable(
        {
            handle: handle,
            connectWith: connect,
            tolerance: 'pointer',
            forcePlaceholderSize: true,
            opacity: 0.8
        })
        .disableSelection();
}


// Logout
function Logout() {
    var _url = "/user/logout";
    var _model = "POST";

    AjaxPost(_url, _model, undefined, function (data) {
        var _rtndata = $.parseJSON(data);

        if (_rtndata.success == true) {
            var couserid = GetStorage("userid");
            if(IsNull(couserid) == false){
                RemoveStorage("userid");
                RemoveStorage("userrole");
            }

            window.location = "/";
        } else {
           swal("Error", _rtndata.msg, "error");
        }
    });
}
// Logout

//#data storage
function GetStorage(key) {
    return localStorage.getItem(key);
}

function SaveStorage(key, data) {
    localStorage.setItem(key, data);
}

function RemoveStorage(key) {
    localStorage.removeItem(key);
}

function ClearStorage() {
    localStorage.clear();
}
//#endregion

//#region GetJSON
function GetJSON(url, args, success) {
    $.getJSON(url, args)
        .done(function (data) {
            success(data);
        }).fail(function (e) {
        });
}

function AjaxPost(url, model, args, success) {
    $.ajax({
        type: model,
        url: url,
        data: args,
        processData: false,
        contentType: false,
        mimeType: 'multipart/form-data',
    }).done(function (data) {
        success(data);
    }).fail(function () {
        swal('提示', '上傳失敗', 'error');
    })
}
//#endregion


//#region Date Functions

Date.prototype.addSeconds = function (seconds) {
    this.setSeconds(this.getSeconds() + seconds);
    return this;
};

Date.prototype.addMinutes = function (minutes) {
    this.setMinutes(this.getMinutes() + minutes);
    return this;
};

Date.prototype.addHours = function (hours) {
    this.setHours(this.getHours() + hours);
    return this;
};

Date.prototype.addDays = function (days) {
    this.setDate(this.getDate() + days);
    return this;
};

Date.prototype.addWeeks = function (weeks) {
    this.addDays(weeks * 7);
    return this;
};

Date.prototype.addMonths = function (months) {
    var dt = this.getDate();

    this.setMonth(this.getMonth() + months);
    var currDt = this.getDate();

    if (dt !== currDt) {
        this.addDays(-currDt);
    }

    return this;
};

Date.prototype.addYears = function (years) {
    var dt = this.getDate();

    this.setFullYear(this.getFullYear() + years);

    var currDt = this.getDate();

    if (dt !== currDt) {
        this.addDays(-currDt);
    }

    return this;
};

function StringToHtmlyyyymmdd(s) {
    var ss = s.toString();
    return ss.substring(0, 4) + ss.substring(5, 7) + ss.substring(8, 10);
}

function StringToHtmlyyyymmdd2(s) {
    var ss = s.toString();
    return ss.substring(6, 10) + ss.substring(0, 2) + ss.substring(3, 5);
}

function DateToHtmlDate(d) {
    var y = "0000" + d.getFullYear();
    var m = "00" + (d.getMonth() + 1);
    var d = "00" + d.getDate();
    return y.substring(y.length, y.length - 4) + "-" + m.substring(m.length, m.length - 2) + "-" + d.substring(d.length, d.length - 2);
}

function DateToString(d) {
    var y = "0000" + d.getFullYear();
    var m = "00" + (d.getMonth() + 1);
    var d = "00" + d.getDate();
    return y.substring(y.length, y.length - 4) + m.substring(m.length, m.length - 2) + d.substring(d.length, d.length - 2);
}

function HtmlDateToString(hd) {
    return hd.replace(/-/g, "").length == 8 ? hd.replace(/-/g, "") : hd.replace("/", "").replace("/", "");
}

function StringToHtmlDate(s) {
    var ss = s.toString();
    var rtn = ""
    if (ss.length == 8) {
        rtn = ss.substring(0, 4) + "-" + ss.substring(4, 6) + "-" + ss.substring(6, 8);
    }
    return rtn;
}

function IsNull(s) {
    return (s == null || s == '' || s === undefined);
}

function GetCurrentDateToString() {
    var currentDate = new Date();
    var yyyy = currentDate.getFullYear().toString();
    var mm = (currentDate.getMonth() + 1).toString();
    var dd = currentDate.getDate().toString();
    return yyyy + (mm[1] ? mm : "0" + mm[0]) + (dd[1] ? dd : "0" + dd[0]) + FormatTimeNoChar(currentDate);
}

function FormatTimeNoChar(t) {
    var h = "00" + t.getHours();
    var m = "00" + t.getMinutes();
    var s = "00" + t.getSeconds();
    return h.substring(h.length, h.length - 2) + m.substring(m.length, m.length - 2) + s.substring(s.length, s.length - 2);
}

//#endregion

