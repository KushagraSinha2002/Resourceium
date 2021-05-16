<template>
  <button
    :id="`loading-button__${text.toLowerCase()}`"
    class="flex justify-center py-2 space-x-3 transition duration-300 transform rounded-15px hover:scale-105 sm:py-3 focus:outline-none bg-primary ring-black ring-1 disabled:bg-gray-700"
    :disabled="disabled"
  >
    <div v-if="!loading" class="text-white">{{ text }}</div>
    <ig-icon
      v-else
      name="loader"
      class="text-white animate-spin-slow"
      no-color
    ></ig-icon>
  </button>
</template>

<script>
export default {
  props: {
    text: {
      type: String,
      required: true,
    },
    disabled: {
      type: Boolean,
      default: false,
    },
    loading: {
      type: Boolean,
      default: false,
    },
  },
}
</script>
