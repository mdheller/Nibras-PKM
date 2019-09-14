(function (a) {
    a.fn.achtung = function (e) {
        var b = (typeof e === "string"), d = Array.prototype.slice.call(arguments, 0), c = "achtung";
        return this.each(function () {
            var f = a.data(this, c);
            if (b && e.substring(0, 1) === "_") {
                return this
            }
            (!f && !b && a.data(this, c, new a.achtung(this))._init(d));
            (f && b && a.isFunction(f[e]) && f[e].apply(f, d.slice(1)))
        })
    };
    a.achtung = function (d) {
        var b = Array.prototype.slice.call(arguments, 0), c;
        if (!d || !d.nodeType) {
            c = a("<div />");
            return c.achtung.apply(c, b)
        }
        this.$container = a(d)
    };
    a.extend(a.achtung, {
        version: "0.3.0",
        $overlay: false,
        defaults: {
            timeout: 2,
            disableClose: false,
            icon: true,
            className: "",
            animateClassSwitch: true,
            showEffects: {opacity: "toggle", height: "toggle"},
            hideEffects: {opacity: "toggle", height: "toggle"},
            showEffectDuration: 500,
            hideEffectDuration: 700
        }
    });
    a.extend(a.achtung.prototype, {
        $container: false, closeTimer: false, options: {}, _init: function (c) {
            var d, b = this;
            c = a.isArray(c) ? c : [];
            c.unshift(a.achtung.defaults);
            c.unshift({});
            d = this.options = a.extend.apply(a, c);
            if (!a.achtung.$overlay) {
                a.achtung.$overlay = a('<div id="achtung-overlay"></div>').appendTo(document.body)
            }
            if (!d.disableClose) {
                a('<span class="achtung-close-button ui-icon ui-icon-close" />').click(function () {
                    b.close()
                }).hover(function () {
                    a(this).addClass("achtung-close-button-hover")
                }, function () {
                    a(this).removeClass("achtung-close-button-hover")
                }).prependTo(this.$container)
            }
            this.changeIcon(d.icon, true);
            if (d.message) {
                this.$container.append(a('<span class="achtung-message">' + d.message + "</span>"))
            }
            (d.className && this.$container.addClass(d.className));
            (d.css && this.$container.css(d.css));
            this.$container.addClass("achtung").appendTo(a.achtung.$overlay);
            if (d.showEffects) {
                this.$container.animate(d.showEffects, d.showEffectDuration)
            } else {
                this.$container.show()
            }
            if (d.timeout > 0) {
                this.timeout(d.timeout)
            }
        }, timeout: function (c) {
            var b = this;
            if (this.closeTimer) {
                clearTimeout(this.closeTimer)
            }
            this.closeTimer = setTimeout(function () {
                b.close()
            }, c * 1000);
            this.options.timeout = c
        }, changeClass: function (c) {
            var b = this;
            if (this.options.className === c) {
                return
            }
            this.$container.queue(function () {
                if (!b.options.animateClassSwitch || /webkit/.test(navigator.userAgent.toLowerCase()) || !a.isFunction(a.fn.switchClass)) {
                    b.$container.removeClass(b.options.className);
                    b.$container.addClass(c)
                } else {
                    b.$container.switchClass(b.options.className, c, 500)
                }
                b.options.className = c;
                b.$container.dequeue()
            })
        }, changeIcon: function (c, d) {
            var b = this;
            if ((d !== true || c === false) && this.options.icon === c) {
                return
            }
            if (d || this.options.icon === false) {
                this.$container.prepend(a('<span class="achtung-message-icon ui-icon ' + c + '" />'));
                this.options.icon = c;
                return
            } else {
                if (c === false) {
                    this.$container.find(".achtung-message-icon").remove();
                    this.options.icon = false;
                    return
                }
            }
            this.$container.queue(function () {
                var e = a(".achtung-message-icon", b.$container);
                if (!b.options.animateClassSwitch || /webkit/.test(navigator.userAgent.toLowerCase()) || !a.isFunction(a.fn.switchClass)) {
                    e.removeClass(b.options.icon);
                    e.addClass(c)
                } else {
                    e.switchClass(b.options.icon, c, 500)
                }
                b.options.icon = c;
                b.$container.dequeue()
            })
        }, changeMessage: function (b) {
            this.$container.queue(function () {
                a(".achtung-message", a(this)).html(b);
                a(this).dequeue()
            })
        }, update: function (b) {
            (b.className && this.changeClass(b.className));
            (b.css && this.$container.css(b.css));
            (typeof(b.icon) !== "undefined" && this.changeIcon(b.icon));
            (b.message && this.changeMessage(b.message));
            (b.timeout && this.timeout(b.timeout))
        }, close: function () {
            var c = this.options, b = this.$container;
            if (c.hideEffects) {
                this.$container.animate(c.hideEffects, c.hideEffectDuration)
            } else {
                this.$container.hide()
            }
            b.queue(function () {
                b.removeData("achtung");
                b.remove();
                if (a.achtung.$overlay && a.achtung.$overlay.is(":empty")) {
                    a.achtung.$overlay.remove();
                    a.achtung.$overlay = false
                }
                b.dequeue()
            })
        }
    })
})(jQuery);