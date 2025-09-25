import "./style.css";
import { Capacitor } from "@capacitor/core";
import {
  NavigationBar,
  NavigationBarColor,
} from "@capgo/capacitor-navigation-bar";

const getRequiredElement = <T extends Element>(selector: string): T => {
  const element = document.querySelector<T>(selector);
  if (!element) {
    throw new Error(`Missing element for selector ${selector}`);
  }
  return element;
};

const app = getRequiredElement<HTMLDivElement>("#app");

const defaultCustomColor = "#2563eb";
const colorChoices: { label: string; value: string }[] = [
  { label: "System Default", value: NavigationBarColor.WHITE },
  { label: "Dark", value: NavigationBarColor.BLACK },
  { label: "Transparent", value: NavigationBarColor.TRANSPARENT },
  { label: "Deep Purple", value: "#5b21b6" },
  { label: "Sunset", value: "#f97316" },
  { label: "Custom (use pickers below)", value: "__custom" },
];

const presetOptions = colorChoices
  .map((choice, index) => {
    const suffix = choice.value === "__custom" ? "" : ` (${choice.value})`;
    const selected = index === 0 ? " selected" : "";
    return `<option value="${choice.value}"${selected}>${choice.label}${suffix}</option>`;
  })
  .join("");

app.innerHTML = `
  <h1>Navigation Bar Showcase</h1>
  <p>Select a color preset or provide a custom hex value, then toggle the button color mode. The plugin
  applies the change when the app runs on a device with a navigation bar (primarily Android).</p>

  <section>
    <fieldset>
      <legend>Color Presets</legend>
      <label for="color-select">Choose a color</label>
      <select id="color-select">${presetOptions}</select>
    </fieldset>
  </section>

  <section>
    <fieldset>
      <legend>Custom Color</legend>
      <label for="custom-color">Pick or type a hex color</label>
      <input id="custom-color" type="color" value="${defaultCustomColor}" />
      <input id="custom-color-text" type="text" value="${defaultCustomColor}" />
    </fieldset>
  </section>

  <section>
    <fieldset>
      <legend>Dark Buttons</legend>
      <div class="switcher">
        <input id="dark-buttons" type="checkbox" checked />
        <label for="dark-buttons">Use dark navigation buttons when the bar is light</label>
      </div>
    </fieldset>
  </section>

  <section>
    <button id="apply">Apply Navigation Bar Style</button>
    <button id="read" style="background:#059669;border-color:#059669;">Read Current Navigation Bar</button>
    <div id="status"></div>
    <div id="preview">Preview</div>
  </section>
`;

const colorSelect = document.querySelector<HTMLSelectElement>("#color-select")!;
const customColorInput =
  document.querySelector<HTMLInputElement>("#custom-color")!;
const customColorText =
  document.querySelector<HTMLInputElement>("#custom-color-text")!;
const darkButtonsInput =
  document.querySelector<HTMLInputElement>("#dark-buttons")!;
const applyButton = document.querySelector<HTMLButtonElement>("#apply")!;
const readButton = document.querySelector<HTMLButtonElement>("#read")!;
const statusEl = document.querySelector<HTMLDivElement>("#status")!;
const preview = document.querySelector<HTMLDivElement>("#preview")!;

const updatePreview = (color: string) => {
  preview.style.background = color;
  preview.style.color = darkButtonsInput.checked ? "#0f172a" : "#f8fafc";
  preview.textContent = `Preview (${color})`;
};

const normalizeHex = (value: string) => {
  const trimmed = value.trim();
  return /^#[0-9a-fA-F]{6}$/.test(trimmed) ? trimmed.toLowerCase() : trimmed;
};

const getCustomColor = () => {
  const textValue = normalizeHex(customColorText.value);
  if (textValue) {
    return textValue;
  }
  return customColorInput.value || defaultCustomColor;
};

const getSelectedColor = () => {
  return colorSelect.value === "__custom"
    ? getCustomColor()
    : colorSelect.value;
};

const setStatus = (message: string, isError = false) => {
  statusEl.textContent = message;
  statusEl.style.color = isError ? "#b91c1c" : "#1e293b";
};

const ensureCustomSelection = (color: string) => {
  const matchedPreset = colorChoices.find((choice) => choice.value === color);
  colorSelect.value = matchedPreset ? matchedPreset.value : "__custom";
};

colorSelect.addEventListener("change", () => {
  const color = getSelectedColor();
  updatePreview(color);
});

customColorInput.addEventListener("input", () => {
  const value = normalizeHex(customColorInput.value);
  customColorText.value = value;
  if (colorSelect.value === "__custom") {
    updatePreview(value);
  }
});

customColorText.addEventListener("input", () => {
  const normalized = normalizeHex(customColorText.value);
  customColorText.value = normalized;
  if (/^#[0-9a-fA-F]{6}$/.test(normalized)) {
    customColorInput.value = normalized;
  }
  if (colorSelect.value === "__custom") {
    updatePreview(getCustomColor());
  }
});

darkButtonsInput.addEventListener("change", () => {
  updatePreview(getSelectedColor());
});

applyButton.addEventListener("click", async () => {
  const color = getSelectedColor();
  updatePreview(color);

  try {
    await NavigationBar.setNavigationBarColor({
      color,
      darkButtons: darkButtonsInput.checked,
    });
    setStatus(
      `Navigation bar updated to ${color} with ${darkButtonsInput.checked ? "dark" : "light"} buttons.`,
    );
  } catch (error) {
    console.error(error);
    setStatus(`Failed to set navigation bar: ${String(error)}`, true);
  }
});

readButton.addEventListener("click", async () => {
  try {
    const state = await NavigationBar.getNavigationBarColor();
    customColorInput.value = state.color;
    customColorText.value = state.color;
    ensureCustomSelection(state.color);
    darkButtonsInput.checked = state.darkButtons;
    updatePreview(state.color);
    setStatus(
      `Current color: ${state.color} | Dark buttons: ${state.darkButtons}`,
    );
  } catch (error) {
    console.error(error);
    setStatus(`Failed to read navigation bar: ${String(error)}`, true);
  }
});

if (!Capacitor.isNativePlatform()) {
  setStatus(
    "Run on a native device or emulator to apply navigation bar changes.",
  );
}

updatePreview(getSelectedColor());
