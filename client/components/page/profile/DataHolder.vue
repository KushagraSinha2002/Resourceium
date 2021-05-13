<template>
  <div
    class="flex flex-col items-center justify-between p-1 border rounded-md sm:border-none md:flex-row"
  >
    <div class="font-semibold text-gray-200 uppercase md:text-lg font-poppins">
      {{ label }}
    </div>
    <div class="flex items-center space-x-3">
      <div class="md:text-lg font-styled-code text-warm-gray-200">
        {{ identifier }}
      </div>
      <ig-icon v-if="editable" name="pen-tool" variant="primary"></ig-icon>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    identifier: {
      type: [String, Number],
      default: '',
      required: true,
    },
    label: {
      type: String,
      required: true,
    },
    editable: {
      type: Boolean,
      default: false,
    },
  },
}
</script>
