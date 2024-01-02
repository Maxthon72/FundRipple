export interface PayPal{
    id: string;
    intent: string;
    paymentMethod: string;
    currency: string;
    total: string;
    description: string;
    state: string;
    createTime: string;
    approvalUrl: string;
    executeUrl: string;
}