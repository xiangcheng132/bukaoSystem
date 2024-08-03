<template>
  <div class="pagination-container">
    <el-pagination
      background
      layout="prev, pager, next, jumper"
      :total="total"
      :page-size="pageSize"
      :current-page.sync="currentPage"
      @current-change="handlePageChange"
      @size-change="handleSizeChange"
    />
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from 'vue';

const props = defineProps({
  total: {
    type: Number,
    required: true,
  },
  pageSize: {
    type: Number,
    required: true,
  },
  currentPage: {
    type: Number,
    required: true,
  },
});

const emits = defineEmits(['update:currentPage']);

const handlePageChange = (page) => {
  emits('update:currentPage', page);
};
const handleSizeChange = (size) => {
  emits('update:pageSize', size);
};
</script>

  // import { scrollTo } from '@/utils/scroll-to'
  
  // export default {
  //   name: 'Pagination',
  //   props: {
  //     total: {
  //       required: true,
  //       type: Number
  //     },
  //     page: {
  //       type: Number,
  //       default: 1
  //     },
  //     limit: {
  //       type: Number,
  //       default: 10
  //     },
  //     pageSizes: {
  //       type: Array,
  //       default () {
  //         return [5, 10, 20, 30, 50]
  //       }
  //     },
  //     layout: {
  //       type: String,
  //       default: 'total, sizes, prev, pager, next, jumper'
  //     },
  //     background: {
  //       type: Boolean,
  //       default: true
  //     },
  //     autoScroll: {
  //       type: Boolean,
  //       default: true
  //     },
  //     hidden: {
  //       type: Boolean,
  //       default: false
  //     }
  //   },
  //   computed: {
  //     currentPage: {
  //       get () {
  //         return this.page
  //       },
  //       set (val) {
  //         this.$emit('update:page', val)
  //       }
  //     },
  //     pageSize: {
  //       get () {
  //         return this.limit
  //       },
  //       set (val) {
  //         this.$emit('update:limit', val)
  //       }
  //     }
  //   },
  //   methods: {
  //     handleSizeChange (val) {
  //       this.$emit('pagination', { page: this.currentPage, limit: val })
  //       if (this.autoScroll) {
  //         scrollTo(0, 800)
  //       }
  //     },
  //     handleCurrentChange (val) {
  //       this.$emit('pagination', { page: val, limit: this.pageSize })
  //       if (this.autoScroll) {
  //         scrollTo(0, 800)
  //       }
  //     }
  //   }
  // }
  <style scoped>
  .pagination-container {
    background: #fff;
  }
  .pagination-container.hidden {
    display: none;
  }
  </style>