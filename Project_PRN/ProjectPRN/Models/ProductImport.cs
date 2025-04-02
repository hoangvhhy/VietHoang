using System;
using System.Collections.Generic;

namespace ProjectPRN.Models;

public partial class ProductImport
{
    public int ImportId { get; set; }

    public int ProductId { get; set; }

    public DateTime? ImportDate { get; set; }

    public int Quantity { get; set; }

    public decimal ImportPrice { get; set; }

    public virtual Product Product { get; set; } = null!;
}
