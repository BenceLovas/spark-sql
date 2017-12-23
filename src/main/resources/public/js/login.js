$(function() {

    const eventHandler = {

        toggleSignUpForm: (event) => {
            $(event.currentTarget).find('svg').toggleClass('fa-caret-down').toggleClass('fa-caret-up');
            $("#signUpForm").slideToggle();
            $("#logInForm").slideUp();
            let text = $("#signUpToggleText").text();
            $("#signUpToggleText").text(text === 'expand' ? 'collapse' : 'expand');

            let $arrow = $("#logInToggle").find('svg');
            if ($arrow.hasClass('fa-caret-up')) {
                $arrow.removeClass('fa-caret-up');
                $arrow.addClass('fa-caret-down');
                $("#logInToggleText").text('expand');
            }
        },

        toggleLogInForm: (event) => {
            $(event.currentTarget).find('svg').toggleClass('fa-caret-down').toggleClass('fa-caret-up');
            $("#logInForm").slideToggle();
            $("#signUpForm").slideUp();
            let text = $("#logInToggleText").text();
            $("#logInToggleText").text(text === 'expand' ? 'collapse' : 'expand');

            let $arrow = $("#signUpToggle").find('svg');
            if ($arrow.hasClass('fa-caret-up')) {
                $arrow.removeClass('fa-caret-up');
                $arrow.addClass('fa-caret-down');
                $("#signUpToggleText").text('expand');
            }
        },

        toggleLabel: (event) => {
            let $text = $(event.target).siblings('.label');
            if ($(event.target).val().length > 0) {
                $text.show("slide", { direction: "left", easing: "easeOutQuad" }, 1000);
                $(event.target).addClass('shift-right').removeClass('shift-left');

            } else if ($(event.target).val().length < 1) {
                $text.hide("slide", { direction: "left", easing: "easeInQuad" }, 1000);
                $(event.target).addClass('shift-left').removeClass('shift-right');
            }
        }
    };

    const eventApplier = {

        toggleSignUpForm: () => $("#signUpToggle").click(eventHandler.toggleSignUpForm),
        toggleLogInForm: () => $("#logInToggle").click(eventHandler.toggleLogInForm),
        toggleLabelOnInputChange: () => $("input").keyup(eventHandler.toggleLabel)
    };

    const main = {

        init: () => {
            eventApplier.toggleSignUpForm();
            eventApplier.toggleLogInForm();
            eventApplier.toggleLabelOnInputChange();
        }
    };

    main.init();
});