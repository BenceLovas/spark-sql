$(function() {

    const eventHandler = {

        toggleLabel: (event) => {
            let text = $(event.target).siblings();
            if ($(event.target).val().length > 0) {
                text.show("slide", { direction: "left", easing: "easeOutQuad" }, 1000);
            } else if ($(event.target).val().length < 1) {
                text.hide("slide", { direction: "left", easing: "easeInQuad" }, 1000);
            }
        }
    };

    const eventApplier = {

        toggleLabelOnInputChange: () => $("input").keyup(eventHandler.toggleLabel)
    };

    const main = {

        init: () => eventApplier.toggleLabelOnInputChange()
    };

    main.init();
});