<template>
  <div>
    <label
      class="text-sm font-bold tracking-wider uppercase font-poppins"
      :for="name"
      :class="{ hidden: !noHideLabel }"
    >
      {{ placeholderText }}
    </label>
    <input
      :id="name"
      :type="inputType"
      :name="name"
      :placeholder="placeholderText"
      class="w-full px-4 py-2 transition bg-transparent border appearance-none hover:shadow-lg rounded-15px border-dark-black border-opacity-40 focus:outline-none focus:border-blue-300"
      @input="handleInput($event.target.value)"
    />
  </div>
</template>

<script>
export default {
  props: {
    name: {
      type: String,
      required: true,
    },
    inputType: {
      type: String,
      default: 'text',
    },
    value: {
      type: String,
      required: true,
    },
    placeholderText: {
      type: String,
      required: true,
    },
    noHideLabel: {
      type: Boolean,
      default: false,
    },
  },
  methods: {
    handleInput(val) {
      this.$emit('input', val)
    },
  },
}
</script>
