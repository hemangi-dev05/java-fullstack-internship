const pricingCards = document.querySelectorAll(".pricing-card");
const selectionMessage = document.getElementById("selection-message");

pricingCards.forEach((card) => {
    const selectButton = card.querySelector(".select-plan");

    selectButton.addEventListener("click", () => {
        pricingCards.forEach((item) => {
            item.classList.remove("selected");
            item.querySelector(".select-plan").textContent = "Select Plan";
        });

        card.classList.add("selected");
        selectButton.textContent = "Selected";

        const courseName = card.querySelector("h2").textContent;
        const coursePrice = card.querySelector(".price").textContent.trim();

        selectionMessage.textContent =
            `You selected the ${courseName} Plan - ${coursePrice}`;
    });
});