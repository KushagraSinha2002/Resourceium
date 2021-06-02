<template>
  <div class="my-5">
    <page-folders-files-container></page-folders-files-container>
  </div>
</template>

<script>
export default {
  middleware: ['auth'],
}
</script>
