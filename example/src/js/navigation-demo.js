import { NavigationBar } from "@capgo/capacitor-navigation-bar";

const colorButtons = document.querySelectorAll("[data-nav-color]");
const getColorButton = document.querySelector("[data-action=get-color]");
const statusEl = document.querySelector("[data-status]");

const formatColor = (value) => {
  if (typeof value !== "string") {
    return "unknown";
  }
  return value.startsWith("#") ? value.toUpperCase() : value;
};

const logStatus = (message, isError = false) => {
  if (!statusEl) {
    return;
  }

  statusEl.textContent = message;
  statusEl.dataset.state = isError ? "error" : "success";
};

const getDarkButtonsPreference = (hex) => {
  if (typeof hex !== "string" || !hex.startsWith("#") || hex.length < 7) {
    return true;
  }

  const r = parseInt(hex.slice(1, 3), 16) / 255;
  const g = parseInt(hex.slice(3, 5), 16) / 255;
  const b = parseInt(hex.slice(5, 7), 16) / 255;

  // Relative luminance formula to decide if navigation buttons should be dark
  const luminance = 0.2126 * r + 0.7152 * g + 0.0722 * b;
  return luminance > 0.5;
};

const setNavigationBarColor = async (color) => {
  try {
    await NavigationBar.setNavigationBarColor({
      color,
      darkButtons: getDarkButtonsPreference(color),
    });
    logStatus(`Navigation bar color set to ${formatColor(color)}`);
  } catch (error) {
    console.error("Failed to set navigation bar color", error);
    logStatus(`Failed to set color: ${error?.message ?? error}`, true);
  }
};

const wireButtons = () => {
  colorButtons.forEach((button) => {
    button.addEventListener("click", () => {
      const color = button.getAttribute("data-nav-color");
      if (!color) {
        return;
      }
      void setNavigationBarColor(color);
    });
  });

  getColorButton?.addEventListener("click", async () => {
    try {
      const { color, darkButtons } = await NavigationBar.getNavigationBarColor();
      logStatus(
        `Current navigation bar color: ${formatColor(color)} (dark buttons: ${darkButtons ? "yes" : "no"})`,
      );
    } catch (error) {
      console.error("Failed to get navigation bar color", error);
      logStatus(`Failed to get color: ${error?.message ?? error}`, true);
    }
  });
};

wireButtons();

