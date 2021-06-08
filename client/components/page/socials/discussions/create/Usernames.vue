<template>
  <div>
    <label
      class="text-sm font-bold tracking-wider uppercase font-poppins"
      for="autosuggest-input"
    >
      {{ label }}
    </label>
    <page-socials-discussions-create-username-input
      @selected="handleSelect"
    ></page-socials-discussions-create-username-input>
  </div>
</template>

<script>
export default {
  props: {
    label: { type: String, required: true },
  },
  methods: {
    handleSelect(val) {
      this.$emit('handleSelect', val)
    },
  },
}
</script>
