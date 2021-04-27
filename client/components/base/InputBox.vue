<template>
  <div
    class="relative transition duration-300 border-2 input-outline rounded-xl focus-within:border-blue-500"
  >
    <input
      :id="name"
      :type="inputType"
      :name="name"
      placeholder=" "
      class="block w-full p-2 text-lg bg-transparent sm:p-3 md:p-4 focus:outline-none"
      @input="handleInput($event.target.value)"
    />
    <label
      :for="name"
      class="absolute top-0 p-2 text-lg text-gray-500 capitalize duration-150 bg-white bg-opacity-0 sm:p-3 md:p-4 cursor-text origin-0 rounded-xl"
    >
      {{ name }}
    </label>
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
      default: '',
    },
  },
  methods: {
    handleInput(val) {
      this.$emit('input', val)
    },
  },
}
</script>

<style scoped>
.input-outline input:focus-within ~ label,
.input-outline input:not(:placeholder-shown) ~ label {
  @apply transform scale-75 -translate-y-4 z-0 ml-0.5 sm:ml-1 md:ml-2 px-1 py-0 text-blue-700 bg-opacity-100;
}
</style>
