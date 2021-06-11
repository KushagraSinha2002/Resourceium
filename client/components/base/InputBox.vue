<template>
  <div>
    <label
      class="text-sm font-bold tracking-wider uppercase font-poppins"
      :for="name"
      :class="{ hidden: !noHideLabel }"
    >
      {{ placeholderText }}
    </label>
    <component
      :is="textarea ? 'textarea' : 'input'"
      :id="name"
      ref="inputBox"
      :value="value"
      :type="inputType"
      :name="name"
      rows="3"
      :placeholder="placeholderText"
      class="w-full px-4 py-2 transition bg-transparent border appearance-none resize-none hover:shadow-lg rounded-15px border-dark-black border-opacity-40 focus:outline-none focus:border-blue-300"
      :autocomplete="!noAutoComplete ? 'on' : 'off'"
      :required="!notRequired"
      @input="handleInput($event.target.value)"
    />
  </div>
</template>

<script>
export default {
  props: {
    name: {
      // The name of the field, should be unique since it would be used in ID.
      type: String,
      required: true,
    },
    inputType: {
      // The input type. Only `text`, `password`, `date` and `email` allowed.
      type: String,
      default: 'text',
    },
    value: {
      // A Vue internal. Should be handled when a `v-model` is added to the component.
      type: String,
      required: true,
    },
    placeholderText: {
      // The text displayed inside the placeholder of the input.
      type: String,
      required: true,
    },
    noHideLabel: {
      // If set to true, the label will not be hidden.
      type: Boolean,
      default: false,
    },
    notRequired: {
      // If set to true, this input box will not be required and the browser checks for
      // this particular field will be disabled.
      type: Boolean,
      default: false,
    },
    focusOnRender: {
      // If set to true, the input box will be focused when it is rendered on screen
      type: Boolean,
      default: false,
    },
    noAutoComplete: {
      // If set to true, the input box will be not show autocomplete suggestions
      type: Boolean,
      default: false,
    },
    textarea: {
      // If set to true, the input box will be focused when it is rendered on screen
      type: Boolean,
      default: false,
    },
  },
  mounted() {
    if (this.focusOnRender) {
      this.$refs.inputBox.focus()
    }
  },
  methods: {
    handleInput(value) {
      this.$emit('input', value)
    },
  },
}
</script>
